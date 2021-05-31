function init() {
    getJSON('http://81.70.41.11:8082/userList', (result) => {
        $.each(result['headerList'], (i, j) => {
            $('#headerList').append('<li class="media">\n' +
                '    <img alt="image" class="mr-3 rounded-circle" width="70"\n' +
                '         src="/assets/img/avatar/avatar-' + (i % 5 + 1) + '.png">\n' +
                '    <div class="media-body">\n' +
                '        <div class="media-right">\n' +
                '            <button href="#" class="btn btn-danger" onclick="onDel(\'' + j['id'] + '\')">删除</button>' +
                '        </div>\n' +
                '        <div class="media-title mb-1"> <a href=' + j['id'] + '"/header/userInfo.html?id=">' + j['id'] + '/' + j['name'] + '</a></div>\n' +
                '        <div class="text-time">' + j['info'] + '</div>\n' +
                '        <div class="media-description text-muted">\n' + j['remark'] +
                '        </div>\n' +
                '    </div>\n' +
                '</li>')
        })
        $.each(result['marketList'], (i, j) => {
            $('#marketList').append('<li class="media">\n' +
                '    <img alt="image" class="mr-3 rounded-circle" width="70"\n' +
                '         src="/assets/img/avatar/avatar-' + (i % 5 + 1) + '.png">\n' +
                '    <div class="media-body">\n' +
                '        <div class="media-right">\n' +
                '            <button href="#" class="btn btn-danger" onclick="onDel(\'' + j['id'] + '\')">删除</button>' +
                '        </div>\n' +
                '        <div class="media-title mb-1"> <a href=' + j['id'] + '"/header/userInfo.html?id=">' + j['id'] + '/' + j['name'] + '</a></div>\n' +
                '        <div class="text-time">' + j['info'] + '</div>\n' +
                '        <div class="media-description text-muted">\n' + j['remark'] +
                '        </div>\n' +
                '    </div>\n' +
                '</li>')
        })
        $.each(result['warehouseList'], (i, j) => {
            $('#warehouseList').append('<li class="media">\n' +
                '    <img alt="image" class="mr-3 rounded-circle" width="70"\n' +
                '         src="/assets/img/avatar/avatar-' + (i % 5 + 1) + '.png">\n' +
                '    <div class="media-body">\n' +
                '        <div class="media-right">\n' +
                '            <button href="#" class="btn btn-danger" onclick="onDel(\'' + j['id'] + '\')">删除</button>' +
                '        </div>\n' +
                '        <div class="media-title mb-1"> <a href=' + j['id'] + '"/header/userInfo.html?id=">' + j['id'] + '/' + j['name'] + '</a></div>\n' +
                '        <div class="text-time">' + j['info'] + '</div>\n' +
                '        <div class="media-description text-muted">\n' + j['remark'] +
                '        </div>\n' +
                '    </div>\n' +
                '</li>')
        })

    })
}

function onDel(id) {
    swal({
        title: '你确定要删除吗',
        text: '删除以后无法恢复',
        icon: 'warning',
        buttons: true,
        dangerMode: true,
    }).then((willDelete) => {
        if (willDelete) {
            getJSON('http://81.70.41.11:8082/delUserInfo?id=' + id, (result) => {
                swal('删除成功', {
                    icon: 'success'
                }).then(() => {
                    window.location.reload()
                })
            })
        }
    });
}

$(document).ready(() => {
    init()
})