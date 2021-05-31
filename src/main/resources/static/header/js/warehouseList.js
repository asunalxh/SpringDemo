function init() {
    getJSON('http://81.70.41.11:8082/warehouseList', (json) => {
        $.each(json, (i, warehouse) => {
            const str = '<tr>\n' +
                '<td>\n' + warehouse['id'] + '</td>\n' +
                '<td>' + warehouse['name'] + '</td>\n' +
                '<td>\n' + warehouse['address'] + '</td>\n' +
                '<td><div class="badge  badge-success">' + warehouse['tel'] + '</div></td>\n' +
                '<td><div class="badge  badge-success">' + warehouse['contacts'] + '</div></td>\n' +
                '' + '<td><a href="warehouseInfo.html?id=' + warehouse['id'] + '" class="btn btn-secondary">Detail</a></td>\n' +
                '</tr>\n'

            $('#list').append(str)
        })
    })
}



$(document).ready(() => {
    init()
})