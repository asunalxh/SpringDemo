marketList = []
warehouseList = []

function init() {
    $('#editId').prop('disabled', 'true')

    getJSON('http://81.70.41.11:8082/marketList', (ans) => {
        $.each(ans, (i, j) => {
            marketList.push('<option value="' + j['id'] + '">' + j['name'] + '</option>')
        })
    })
    getJSON('http://81.70.41.11:8082/warehouseList', (ans) => {
        $.each(ans, (i, j) => {
            warehouseList.push('<option value="' + j['id'] + '" >' + j['name'] + '</option>')
        })
    })

}

function switchList() {
    key = $('#typeList').val()
    $('#list').empty()
    $('#list').removeAttr('disabled')

    if (key === '总部') {
        $('#list').attr('disabled', 'disabled')
    } else if (key === '超市') {
        for (var i in marketList) {
            $('#list').append(marketList[i])
        }
    } else if (key === '仓库') {
        for (var i in warehouseList) {
            $('#list').append(warehouseList[i])
        }
    }
}

$('#typeList').change(() => {
    switchList()
})

$('#submitBtn').click(() => {
    flag = true
    if (!check('editID', /^\w+$/)) flag = false
    if (!check('editName', /^.+$/)) flag = false
    if (!check('editPass', /^.+$/)) flag = false
    if (!check('editPass2', /^.+$/)) flag = false
    if ($('#editPass').val() !== $('#editPass2').val()) {
        $('#editPass').css('border-color', 'red')
        $('#editPass2').css('border-color', 'red')
        flag = false
    }

    if (flag) {
        postJSON('http://81.70.41.11:8082/insertUserInfo', {
            id: $('#editID').val(),
            name: $('#editName').val(),
            type: $('#typeList').val(),
            info: $('#list').val(),
            password: $('#editPass').val(),
            remark: $('#editRemark').val(),
        }, (result) => {
            swal('添加成功', {
                icon: 'success'
            }).then(() => {
                window.location.href='./userList.html'
            })
        })
    }

})


$(document).ready(() => {
    init()
})