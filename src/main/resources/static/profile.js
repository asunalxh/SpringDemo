$("#typeList").change(() => {
    let typeStr = $("#typeList").val()
    $('#list option').attr('hidden', 'hidden')
    if (typeStr === '总部') {
        $('#list option.header').removeAttr('hidden')
    } else if (typeStr === '超市') {
        $('#list option.market').removeAttr('hidden')
    } else {
        $('#list option.warehouse').removeAttr('hidden')
    }
    $('#list option[hidden!="hidden"]:first').prop('selected','selected')
})
