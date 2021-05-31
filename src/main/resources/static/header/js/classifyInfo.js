id = getQueryVariable('id')

function init() {
    if (id) {
        getJSON('http://81.70.41.11:8082/classifyInfo?id=' + id, (result) => {
            $('#classifyName').val(result['name'])
            $.each(result['list'], (i, goods) => {
                const str = '<tr>\n' +
                    '<td>\n' + goods['id'] + '</td>\n' +
                    '<td>' + goods['name'] + '</td>\n' +
                    '<td>\n' + goods['price'] + '元 / ' + goods['unit'] + '</td>\n' +
                    '<td>' + goods['barcode'] + '</td>\n' +
                    '<td><div class="badge  badge-success">' + goods['classify'] + '</div></td>\n' +
                    '' + '<td><a href="goodsInfo.html?id=' + goods['id'] + '" class="btn btn-secondary">Detail</a></td>\n' +
                    '</tr>\n'

                $('#list').append(str)
            })
        })
    } else {
        $('h1').text('添加商品分类')
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
            getJSON('http://81.70.41.11:8082/delClassifyInfo?id=' + id, (result) => {
                swal('删除成功', {
                    icon: 'success',
                }).then(() => {
                    window.location.href = '../../../templates/header/classifyList.html'
                })
            })

        }
    });
}

function updateInfo() {
    if (!check('classifyName', /^.+$/))
        return

    info = {
        'name': $('#classifyName').val()
    }

    url = 'http://81.70.41.11:8082'
    if (id) {
        info['id'] = id
        url += '/updateClassifyInfo'
    } else url += '/insertClassifyInfo'

    postJSON(url, info, (result) => {
        swal('修改成功', {
            icon: 'success'
        }).then(() => {
            window.location.href = 'classifyList.html'
        })
    })
}

$(document).ready(() => {
    init()
})