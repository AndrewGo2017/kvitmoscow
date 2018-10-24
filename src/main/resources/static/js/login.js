/*
used for login page only
 */

/*
gets url parameter
 */
var loginPageStatus = getValueFromUrl('?');

/*
due to jquery dialog show behavior, login dialog comes focused instead of error one.
to prevent it i'm using call back function as showErrorMessage() parameter.
setTimeout('duration time') may be used as an alternative.
 */
$(function () {
    if (loginPageStatus === "error"){
        showErrorMessage("Указан неверный пользователь или пароль!",null, showLogInDialog)
    } else {
        showLogInDialog();
    }
});

/*
shows login dialog
 */
function showLogInDialog() {
    $('#user').selectmenu();
    $('#loginDialog').dialog({
        show: {effect: "explode", duration: 700},
        title: 'Авторизация',
        autoOpen: true,
        modal: true,
        closeOnEscape: false,
        width: 400,
        height: 'auto',
        open: function(event, ui) {
            $('#loginDialog').removeClass('invisible');
            $(".ui-dialog-titlebar-close", ui.dialog | ui).hide();
        }
    });
}