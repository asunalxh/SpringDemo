userID = getQueryVariable('id')
marketList = []
warehouseList = []

function init() {

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

    URL = `http://81.70.41.11:8082/userInfo?id=${userID}`

    getJSON(URL, (result) => {
        $('#name').text(result['name'])
        $('#type').text(result['type'])
        $('#editName').val(result['name'])
        $('#editId').val(result['id'])
        $("#typeList").val(result['type'])
        switchList()
        $("#list").val(result['info'])
        $('#remark').append(result['remark'])
        $('#editRemark').val(result['remark'])

        if (!userID) {
            $('#typeList').attr('disabled', 'disabled')
            $('#list').attr('disabled', 'disabled')
        }
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

$('#commitBtn').click(() => {
    if ($('#editPass').val() !== $('#editPass2').val()) {
        $('#editPass').css('border-color', 'red')
        $('#editPass2').css('border-color', 'red')
    }else{
        info = {
            id: $('#editId').val(),
            name: $('#editName').val(),
            remark: $('#editRemark').val(),
            password: $('#editPass').val()
        }

        if(userID){
            info['type']= $('#typeList').val()
            info['info'] = $('#list').val()
        }

        postJSON('http://81.70.41.11:8082/updateUserInfo', info, (result) => {
            swal('修改成功', {
                icon: 'success'
            }).then(() => {
                window.location.reload()
            })
        })
    }

})


$(document).ready(() => {
    init()
})