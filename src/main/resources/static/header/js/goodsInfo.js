const myID = getQueryVariable('id')
function init() {
    getJSON('http://81.70.41.11:8082/classifyList', (json) => {
        $.each(json, (index, x) => {
            $('#classify').append(
                '<option value="' + x['id'] + '">' + x['name'] + '</option>'
            )
        })
    })

    if (myID) {
        getJSON('http://81.70.41.11:8082/goodsInfo?id=' + myID, (result) => {
            json = result
            $('#id').val(myID)
            $('#name').val(json['name'])
            $('#barcode').val(json['barcode'])
            $('#price').val(json['price'])
            $('#unit').val(json['unit'])
            $('#id').prop('disabled', 'true')
            $("#classify option:contains('" + json['classify'] + "')").attr("selected", true);
        })
    } else {
        $('h1').text('添加商品信息')
        $('#delBtn').hide()
    }
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
            getJSON('http://81.70.41.11:8082/delGoodsInfo?id=' + $('#id').val(), (result) => {
                swal('删除成功', {
                    icon: 'success',
                }).then(() => {
                    window.location.href = 'goodsList.html'
                })
            })

        }
    });
}

function updateInfo() {
    var flag = true
    if (!check('id', /^\w{1,8}$/)) flag = false
    if (!check('name', /^.+$/)) flag = false
    if (!check('barcode', /^\d{13}$/)) flag = false
    if (!check('price', /^.+$/)) flag = false
    if (!check('unit', /^.+$/)) flag = false

    if (!flag)
        return

    var url = 'http://81.70.41.11:8082'
    if (myID)
        url += '/updateGoodsInfo'
    else
        url += '/insertGoodsInfo'
    postJSON(url, {
            id: $('#id').val(),
            name: $('#name').val(),
            barcode: $('#barcode').val(),
            classify: $('#classify').val(),
            price: $('#price').val(),
            unit: $('#unit').val()
        },
        (result) => {
            swal('修改成功', {
                icon: 'success'
            }).then(() => {
                window.location.href = 'goodsInfo.html?id=' + $('#id').val();
            });
        })

}


$(document).ready(() => {
    init()
})