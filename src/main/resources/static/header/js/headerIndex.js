var applyList = []
var supplyList = []

function init() {
    getJSON('http://81.70.41.11:8082/count', (result) => {
        $('#marketCount').text(result['marketCount'])
        $('#warehouseCount').text(result['warehouseCount'])
        $('#applyCount').text(result['applyCount'])
        $('#supplyCount').text(result['supplyCount'])
    })

    getJSON('http://81.70.41.11:8082/applyList', (result) => {
        $.each(result, (i, market) => {
            let tempList = []
            $.each(market['list'], (j, apply) => {
                tempList.push(
                    `<tr>
                        <td>${apply['goodsID']}</td>
                        <td>${apply['goodsName']}</td>
                        <td>${apply['goodsBarcode']}</td>
                        <td>
                            <div class="badge badge-success">${apply['num']}</div>
                        </td>
                        <td>${apply['goodsPrice']}元/${apply['goodsUnit']}</td>
                    </tr>`)
            })
            applyList.push({
                title: market['marketName'],
                code: tempList
            })
        })
        switchApply(1)
    })
    getJSON('http://81.70.41.11:8082/supplyList', (result) => {
        $.each(result, (i, warehouse) => {
            let tempList = []
            $.each(warehouse['list'], (j, apply) => {
                tempList.push(
                    `<tr>
                        <td>${apply['goodsID']}</td>
                        <td>${apply['goodsName']}</td>
                        <td>${apply['goodsBarcode']}</td>
                        <td>
                            <div class="badge badge-success">${apply['num']}</div>
                        </td>
                        <td>${apply['goodsPrice']}元/${apply['goodsUnit']}</td>
                    </tr>`)
            })
            supplyList.push({
                title: warehouse['warehouseName'],
                code: tempList
            })
        })
        switchSupply(1)
    })
}

function switchApply(page) {
    $('#applyList tbody').empty()
    $('#applyList tbody').append(
        `<tr>
            <th>商品序号</th>
            <th>名称</th>
            <th>条码</th>
            <th>数量</th>
            <th>单位</th>
        </tr>`
    )
    $('#applyList h4').text(applyList[page - 1]['title'])
    $.each(applyList[page - 1]['code'], (i, h) => {
        $('#applyList tbody').append(h)
    })

    let start = 1;
    if (page === applyList.length)
        start = page - 2 >= 1 ? page - 2 : 1;
    else
        start = page - 1 >= 1 ? page - 1 : 1;
    let end = start + 2 <= applyList.length ? start + 2 : applyList.length

    $('#applyList .card-footer ul').empty()
    $('#applyList .card-footer ul').append(
        `<li class="page-item">
            <button class="page-link" onclick="switchApply(${page - 1 > 0 ? page - 1 : 1})">
            <i class="fas fa-chevron-left"></i></button>
        </li>`)
    for (i = start; i <= end; i++) {
        $('#applyList .card-footer ul').append(
            `<li class="page-item ${i === page ? 'active' : ''}">
                <button class="page-link" onclick="switchApply(${i})" value="${i}">${i}</button>
            </li>`)
    }
    $('#applyList .card-footer ul').append(
        `<li class="page-item">
            <button class="page-link" onclick="switchApply(${page + 1 <= applyList.length ? page + 1 : page})"><i class="fas fa-chevron-right"></i></button>
        </li>`)
    $(`#applyList .card-footer button[value=${page}]`).append(`<span class="sr-only">(current)</span>`)

}

function switchSupply(page) {
    $('#supplyList tbody').empty()
    $('#supplyList tbody').append(
        `<tr>
            <th>商品序号</th>
            <th>名称</th>
            <th>条码</th>
            <th>数量</th>
            <th>单位</th>
        </tr>`
    )
    $('#supplyList h4').text(supplyList[page - 1]['title'])
    $.each(supplyList[page - 1]['code'], (i, h) => {
        $('#supplyList tbody').append(h)
    })

    let start = 1;
    if (page === supplyList.length)
        start = page - 2 >= 1 ? page - 2 : 1;
    else
        start = page - 1 >= 1 ? page - 1 : 1;
    let end = start + 2 <= supplyList.length ? start + 2 : supplyList.length

    $('#supplyList .card-footer ul').empty()
    $('#supplyList .card-footer ul').append(
        `<li class="page-item">
            <button class="page-link" onclick="switchSupply(${page - 1 > 0 ? page - 1 : 1})">
            <i class="fas fa-chevron-left"></i></button>
        </li>`)
    for (i = start; i <= end; i++) {
        $('#supplyList .card-footer ul').append(
            `<li class="page-item ${i === page ? 'active' : ''}">
                <button class="page-link" onclick="switchSupply(${i})" value="${i}">${i}</button>
            </li>`)
    }
    $('#supplyList .card-footer ul').append(
        `<li class="page-item">
            <button class="page-link" onclick="switchSupply(${page + 1 <= supplyList.length ? page + 1 : page})"><i class="fas fa-chevron-right"></i></button>
        </li>`)
    $(`#supplyList .card-footer button[value=${page}]`).append(`<span class="sr-only">(current)</span>`)

}


$(document).ready((e) => {
    init()
})