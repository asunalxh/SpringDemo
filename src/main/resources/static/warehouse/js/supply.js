function init() {
    getJSON('http://81.70.41.11:8082/goodsList', (result) => {
        $.each(result, (i, goods) => {
            $('#list').append(`<tr id="${goods['id']}">
                    <td class="p-0 text-center">
                        <div class="custom-checkbox custom-control">
                            <input type="checkbox" data-checkboxes="mygroup"
                                   class="custom-control-input" id="checkbox-${goods['id']}">
                            <label for="checkbox-${goods['id']}"
                                   class="custom-control-label">&nbsp;</label>
                        </div>
                    </td>
                    <td>${goods['id']}</td>
                    <td>${goods['name']}</td>
                    <td>${goods['barcode']}</td>
                    <td>${goods['price']} / ${goods['unit']}
                    <td>
                        <div class="badge badge-success">${goods['classify']}</div>
                    </td>
                    <td><input type="number" class="col-2 px-0" value="0" ></td>
                </tr>`)
        })
        getJSON('http://81.70.41.11:8082/supplyInfoList', (result) => {
            $.each(result,(i,apply)=>{
                $(`#${apply['goods']} input[type=number]`).val(apply['num'])
                $(`#${apply['goods']} input[type=checkbox]`).attr('checked','checked')
            })
        })
    })

}

$('#submitBtn').click(() => {
    ans = []
    $.each($('tr'), (i, x) => {
        id = $(x).attr('id')
        if (id && $(x).find('input[type=checkbox]').is(':checked')) {
            ans.push({
                goods: $(x).attr('id'),
                num: $(x).find('input[type=number]').val()
            })
        }
    })
    console.log(ans)
    console.log(JSON.stringify(ans))
    postJSON('http://81.70.41.11:8082/updateSupplyInfo', JSON.stringify(ans), (result) => {
        swal('修改成功', {
            icon: 'success'
        }).then(() => {
            window.location.reload()
        })
    })
})

$('#searchForm').submit((e) => {
    e.preventDefault()
    key = $('#search').val()
    $.each($('#list tr'), (ind, x) => {
        if (ind !== 0 && $(x).text().search(key) === -1) {
            $(x).hide()
        } else $(x).show()

    })
})

$("[data-checkboxes]").each(function () {
    var me = $(this),
        group = me.data('checkboxes'),
        role = me.data('checkbox-role');

    me.change(function () {
        var all = $('[data-checkboxes="' + group + '"]:not([data-checkbox-role="dad"])'),
            checked = $('[data-checkboxes="' + group + '"]:not([data-checkbox-role="dad"]):checked'),
            dad = $('[data-checkboxes="' + group + '"][data-checkbox-role="dad"]'),
            total = all.length,
            checked_length = checked.length;

        if (role == 'dad') {
            if (me.is(':checked')) {
                all.prop('checked', true);
            } else {
                all.prop('checked', false);
            }
        } else {
            if (checked_length >= total) {
                dad.prop('checked', true);
            } else {
                dad.prop('checked', false);
            }
        }
    });
});



$(document).ready((e) => {
    init()
})