isUpdate = false

function init() {
    id = getQueryVariable('id')
    getJSON('http://81.70.41.11:8082/warehouseList', (result) => {
        $.each(result, (i, warehouse) => {
            $('tbody').append(
                `<tr id="${warehouse['id']}">
                <td>${warehouse['id']}</td>
                <td>${warehouse['name']}</td>
                <td><input type="number" value="0"></td>
                <td>KM</td>
            </tr>`
            )
        })

        if (id) {
            isUpdate = true
            getJSON('http://81.70.41.11:8082/marketInfo?id=' + id, (result) => {
                json = result
                $('#id').val(id)
                $('#name').val(json['name'])
                $('#address').val(json['address'])
                $('#price').val(json['price'])
                $('#unit').val(json['unit'])
                $('#contacts').val(json['contacts'])
                $('#tel').val(json['tel'])
                $('#id').prop('disabled', 'true')

            })
            getJSON(`http://81.70.41.11:8082/disInfoListOfMarket?id=${id}`, (result) => {
                $.each(result, (i, dis) => {
                    $(`#${dis['warehouse']} input`).val(dis['dis'])
                })
            })
        } else {
            $('h1').text('添加超市信息')
            $('#delBtn').hide()
        }
    })


}

function delInfo() {
    swal({
        title: '你确定要删除吗',
        text: '删除以后无法恢复',
        icon: 'warning',
        buttons: true,
        dangerMode: true,
    }).then((willDelete) => {
        if (willDelete) {
            getJSON('http://81.70.41.11:8082/delMarketInfo?id=' + $('#id').val(), (result) => {

                swal('删除成功', {
                    icon: 'success',
                }).then(() => {
                    window.location.href = 'marketList.html';
                })

            })

        }
    });
}

function updateInfo() {
    flag = true
    if (!check('id', /^\w{1,8}$/)) flag = false
    if (!check('name', /^.+$/)) flag = false
    if (!check('address', /^.+$/)) flag = false
    if (!check('tel', /^\d*$/)) flag = false
    if (!check('contacts', /^.*$/)) flag = false

    if (!flag)
        return

    url = 'http://81.70.41.11:8082'
    if (isUpdate)
        url += '/updateMarketInfo'
    else
        url += '/insertMarketInfo'

    let disList = []
    $.each($('tbody tr'), (i, dis) => {
        disList.push({
            market: $('#id').val(),
            warehouse: $(dis).attr('id'),
            dis: $(dis).find('input').val()
        })

    })

    postJSON(url, JSON.stringify({
        marketInfo: {
            id: $('#id').val(),
            name: $('#name').val(),
            address: $('#address').val(),
            tel: $('#tel').val(),
            contacts: $('#contacts').val()
        },
        disList: disList
    }), (result) => {
        swal('修改成功', {
            icon: 'success'
        }).then(() => {
            window.location.href = './marketInfo.html?id=' + $('#id').val();
        });
    })

}

$(document).ready(() => {
    init()
})