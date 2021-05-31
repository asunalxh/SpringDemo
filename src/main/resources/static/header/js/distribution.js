applyList = []
supplyList = []

function init() {
    getJSON('http://81.70.41.11:8082/applyList', (result) => {
        applyList = result
        getJSON('http://81.70.41.11:8082/supplyList', (result) => {
            supplyList = result
            $.each(result, (i, warehouse) => {
                $('.section-body').append(
                    `<div class="col-12" id="${warehouse['warehouseID']}">
                        <div class="card">
                            <div class="card-header">
                                <h4>${warehouse['warehouseName']}</h4>
                                <div class="card-header-form">
                                    <form>
                                        <div class="input-group">
                                            <input type="text" class="form-control" placeholder="Search">
                                            <div class="input-group-btn">
                                                <button class="btn btn-primary"><i class="fas fa-search"></i></button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            <div class="card-body p-0">
                                <div class="table-responsive">
                                    <table class="table table-striped">
                                        <tbody>
                                        <tr class="table-header">
                                            <th>商品名</th>
                                            <th>库存</th>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>`)
                $.each(applyList, (j, market) => {
                    $(`#${warehouse['warehouseID']} .table-header`).append(`<td>${market["marketName"]}</td>`)
                })
                $.each(warehouse['list'], (j, supply) => {
                    $(`#${warehouse['warehouseID']} tbody`).append(`
                            <tr class="${supply['goodsID']}">
                                <td>${supply['goodsName']}</td>
                                <td>${supply['num']}</td>                          
                        </tr>`)
                    $.each(applyList, (k, apply) => {
                        $(`#${warehouse['warehouseID']} .${supply['goodsID']}`).append(`
                                <td><input class="${apply['marketID']}" type="number" style="width: 2.5rem" value="0" min="0" disabled="disabled"> </td>
                            `)
                    })
                })
            })
            $.each(applyList, (i, market) => {
                $.each(market['list'], (j, apply) => {
                    $(`.${apply['goodsID']} .${apply['marketID']}`).removeAttr('disabled')
                })
            })
            getJSON('http://81.70.41.11:8082/distributionList', (result) => {
                $.each(result, (i, distribution) => {
                    $(`#${distribution['warehouse']} .${distribution['goods']} .${distribution['market']}`).val(distribution['num'])
                })
            })
        })

    })
}

function updateInfo() {
    let list = []
    $.each(supplyList, (i, warehouse) => {
        $.each(warehouse['list'], (j, supply) => {
            $.each($(`#${warehouse['warehouseID']} .${supply['goodsID']} input[disabled!=disabled]`), (k, val) => {
                if ($(val).val() > 0) {
                    list.push({
                        'market': $(val).attr('class'),
                        'warehouse': warehouse['warehouseID'],
                        'goods': supply['goodsID'],
                        'num': $(val).val()
                    })
                }
            })
        })
    })
    postJSON('http://81.70.41.11:8082/updateDistribution', JSON.stringify(list), (result) => {
        swal('修改成功', {
            icon: 'success'
        }).then(() => {
            window.location.reload()
        })
    })
}

function autoDistribute() {
    getJSON("http://81.70.41.11:8082/autoDistribute", (result) => {
        $.each(result, (i, distribute) => {
            $(`#${distribute['warehouse']} .${distribute['goods']} .${distribute['market']} `).val(distribute['num'])
        })
    })
}


$(document).ready((e) => {
    init()
})