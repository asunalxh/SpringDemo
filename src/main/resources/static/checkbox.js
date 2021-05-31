$("tr").each(function () {
    var me = $(this).find('input[type=checkbox]'),
        inputNum = $(this).find(`input[name="num"]`),
        inputId = $(this).find(`input[name="id"]`)

    var role = me.data('checkbox-role');

    me.change(function () {
        var all = $('input[type=checkbox]:not([data-checkbox-role="dad"])'),
            checked = $('input[type=checkbox]:not([data-checkbox-role="dad"]):checked'),
            dad = $('input[type=checkbox][data-checkbox-role="dad"]'),
            total = all.length,
            checked_length = checked.length;

        var allNum = $('input[name="num"]'),
            allId = $('input[name="id"]')

        if (role === 'dad') {
            if (me.is(':checked')) {
                all.prop('checked', true);
                allNum.removeAttr("disabled")
                allId.removeAttr("disabled")
            } else {
                all.prop('checked', false);
                allNum.attr('disabled', 'disabled')
                allId.attr('disabled', 'disabled')
            }
        } else {
            if (me.is(':checked')) {
                inputId.removeAttr('disabled')
                inputNum.removeAttr('disabled')
            } else {
                inputId.attr("disabled", 'disabled')
                inputNum.attr("disabled", 'disabled')
            }

            if (checked_length >= total) {
                dad.prop('checked', true);
            } else {
                dad.prop('checked', false);
            }
        }
    });
});