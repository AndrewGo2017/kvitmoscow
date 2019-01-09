/*
uses for most (all for now) html pages
 */

/*
gets entity name from url string.
it gives a chance to have universal js code for viewing most of db tables and handling crud operations on each of the table (entity).
*/
var entity = getValueFromUrl('/', true);

/*
after page loaded
 */
$(function () {
    $('.menu').menu();
    $('.select').selectmenu();

    //creates functions dialog
    $('#functions').on('click', function (e) {
        e.preventDefault();
        var functionDialog = $('#functionDialog');
        functionDialog.removeClass('invisible');
        functionDialog.dialog({
            autoOpen: true,
            show: {effect: "fade", duration: 400},
            modal: true,
            width: 'auto'
        });
    });

    //fills main table
    fillMainTable();

    //creates create/update dialog with empty data in it
    if (!$('.noEdit').length && $('#main_edit_create_dialog').length) { // not all pages need to have edit dialog
        $.ajax({
            async: false,
            url: entity + '/' + 0,
            success: function (data) {
                $("#dialog").html(data);
                var dialog = $('#main_edit_create_dialog');
                dialog.dialog({
                    autoOpen: false,
                    show: {effect: "drop", duration: 400},
                    hide: {effect: "fold", duration: 400},
                    modal: true,
                    width: 'auto',
                    close: function () {
                        $('.dialog-data').val('');
                    }
                });
            },
            error: function (xhr) {
                var err = JSON.parse(xhr.responseText);
                showErrorMessage(err.message, 'Ошибка ' + err.status);
            }
        });
    }

    //makes body visible after all jquery widgets created.
    //don't wanna see how plain object becomes an jquery widget.
    $('body').removeClass('invisible');

    //create progress dialog (load effect)
    $('#progressbar').progressbar({
        value: false
    }).css('height', '40');
    $('#loadDialog').dialog({
        show: {effect: "fade", duration: 500},
        autoOpen: false,
        modal: true,
        height: 100,
        width: 150,
        closeOnEscape: false,
        open: function(event, ui) {
            $(".ui-dialog-titlebar-close", ui.dialog | ui).hide();
        }
    });

    //creates calendar wodget
    activateCalendar();

    //handles the look of upload file form
    $('#uploadFile').on('change', function () {
        if (this.files !== 0){
            $('#sentFile').removeClass('invisible');
            $('#labelFileName').text('Выбран файл ' + this.files[0].name);
        } else {
            $('#sentFile').addClass('invisible');
            $('#labelFileName').text('Выбрать файл');
        }
    });



    // $('#fileupload').submit(function() {
    //     showLoadEffect(true);
    //     this.submit();
    //     showLoadEffect(false);
    //     return false;
    // });

    //shows upload status
    //0 - nothing to show; 1 - success; else - error
    var status = $('#status').val();
    if (status !== undefined ){
        if (status === '1'){
            showInfo("Файл успешно загружен!")
        } else if (status === '0') {
        } else{
            showInfo("Произошла ошибка при загрузке файла!\n" + status);
        }
    }

    //hide add button if it is constants
    // if (entity.indexOf('constant') >= 0){
    //     $('#add').addClass('invisible');
    // }

    //uploads file
    $("#sentFile").click(function (e) {
        e.preventDefault();
        sentFile();
    });

});

function sentFile() {
    showLoadEffect(true);

    var form = $('#fileupload')[0];
    var data = new FormData(form);

    // var userSettingId = $('#userSetting').val();
    // var functionId = $('#function').val();

    $.ajax({
        type: "POST",
        xhrFields: {
                responseType: 'arraybuffer'
        },
        enctype: 'multipart/form-data',
        url: "",
        data: data,
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data, d1, d2) {
            if ( d2.getResponseHeader('err') !== null){
                if ('TextDecoder' in window) {
                    var dataView = new DataView(data);
                    var decoder = new TextDecoder('utf-8');
                    var response = JSON.parse(decoder.decode(dataView));
                    console.log('1');
                } else {
                    // Fallback decode as ASCII
                    console.log('2');
                    var string = btoa(unescape(encodeURIComponent(string))),
                        charList = string.split(''),
                        uintArray = [];
                    for (var i = 0; i < charList.length; i++) {
                        uintArray.push(charList[i].charCodeAt(0));
                    }

                    var encodedString = String.fromCharCode.apply(null, new Uint8Array(data));
                    var decodedString = decodeURIComponent(escape(encodedString));
                    var response = JSON.parse(decodedString);
                }

                showErrorMessage(response.message)
            } else {
                var fileName = d2.getResponseHeader('fileName');

                handleBlob(data, fileName);
            }
        },
        complete: function () {
            showLoadEffect(false);
        }
    });
}

function handleBlob(data, fileName){
    var blob = new Blob([data], {type: "application/octet-stream"});

    if(window.navigator.msSaveOrOpenBlob) {
        window.navigator.msSaveOrOpenBlob(blob, fileName);
    } else {

        var a = document.createElement('a');
        var url = window.URL.createObjectURL(blob);
        a.href = url;
        a.download = fileName;
        document.body.appendChild(a);
        a.click();

        setTimeout(function(){
            document.body.removeChild(a);
            window.URL.revokeObjectURL(url);
        }, 100);
    }
}

/*
shows confirmation dialog and removes selected row
 */
function removeRow(id) {
    var confirm_dialog = $("#confirm-dialog");
    confirm_dialog.prop('title', 'Удаление');
    confirm_dialog.removeClass('invisible');
    $("#confirm-dialog-text").text('Элемент будет удален. Вы уверены?');
    confirm_dialog.dialog({
        show: {effect: "drop", duration: 400},
        hide: {effect: "fold", duration: 400},
        resizable: false,
        height: "auto",
        width: "auto",
        modal: true,
        buttons: {
            Да: function () {
                $.ajax({
                    method: 'DELETE',
                    url: entity + '/' + id,
                    success: function () {
                        fillMainTable();
                    },
                    error:function (xhr) {
                        var err = JSON.parse(xhr.responseText);
                        showErrorMessage(err.message, 'Ошибка ' + err.status);
                    }
                });
                $(this).dialog('close');
            },
            Отмена: function () {
                $(this).dialog('close');
            }
        }
    });
}

/*
shows create dialog
 */
function createRow() {
    var dialog = $('#main_edit_create_dialog');
    var dialog_data = $('form[name="detailsForm"]');

    var dialog_buttons = {
        Добавить: function () {
            createOrUpdate(dialog_data, dialog);
        },
        Отмена: function () {
            dialog.dialog('close');
        }
    };

    disableSelect();
    dialog.dialog('option', 'buttons', dialog_buttons);
    dialog.dialog('open');
}

/*
shows update dialog and fills it with data using row id
 */
function updateRow(id) {
    showLoadEffect(true);
    $.ajax({
        url: entity + '/' + id,
        success: function (data) {
            $("#dialog").html(data);
            activateCalendar();
            var dialog = $('#main_edit_create_dialog');
            var dialog_data = $('form[name="detailsForm"]');

            var dialog_buttons;

            //hide add button if it is constants
            if (entity.indexOf('constant') >= 0) {
            dialog_buttons = {
                Изменить: function () {
                    createOrUpdate(dialog_data, dialog);
                },
                Отмена: function () {
                    dialog.dialog('close');
                }
            };
            } else {
                dialog_buttons = {
                    Изменить: function () {
                        createOrUpdate(dialog_data, dialog);
                    },
                    Добавить: function () {
                        dialog_data.find('input[name=id]').val('');
                        createOrUpdate(dialog_data, dialog);
                    },
                    Отмена: function () {
                        dialog.dialog('close');
                    }
                };
            }
            disableSelect();
            dialog.dialog('option', 'buttons', dialog_buttons);
            dialog.dialog('option', 'open', function () {
                var d = $(this).dialog("widget");
                if ($('#add').hasClass('invisible')){
                    d.find(".ui-dialog-buttonpane button:eq(1)").hide();
                }
            });
            dialog.dialog('open');
        },
        error:function (xhr) {
            var err = JSON.parse(xhr.responseText);
            showErrorMessage(err.message, 'Ошибка ' + err.status);
        },
        complete:function(){
            showLoadEffect(false);
        }
    });


}

/*
creates or updates table row with form data
 */
function createOrUpdate(data, dialog) {
    showLoadEffect(true);
    //disabled objects ain't submitted
    $('.parameterized').attr('disabled', false);
    $.post("", data.serialize())
        .done(function () {
            showLoadEffect(false);
            fillMainTable();
            dialog.dialog('close');
        })
        .fail(function (xhr) {
            var err = JSON.parse(xhr.responseText);
            showErrorMessage(err.message, 'Ошибка ' + err.status);
        })
        .always(function () {
            showLoadEffect(false);
        })
}

/*
fills table with data
 */
var tableCondition = $('#tableCondition').val(); // some condition if all data is not needed
function fillMainTable() {
    var str = tableCondition !== undefined ? tableCondition : '';
    var mainTable = $("#mainTable");
    if (mainTable.length) {
        mainTable.load(entity + '/all' + str, function () {
            setMainTable();
        });
    } else{
        setMainTable(false, false);
    }
}

/*
creates jquery table
 */
function setMainTable(isSearching, isPaging) {
    isSearching = typeof isSearching !== 'undefined' ? isSearching : true;
    isPaging = typeof isPaging !== 'undefined' ? isPaging : true;

    var id = 0;
    var elem = $('.main-table th');
    elem.each(function(){
        if( $(this).text()==='Id') {
            id = elem.index(this);
        }
    });

    $('.main-table').DataTable({
        searching : isSearching,
        paging : isPaging,
        info : isPaging,
        language: {
            "processing": "Подождите...",
            "search": "Поиск:",
            "lengthMenu": "Показать _MENU_ записей",
            "info": "Записи с _START_ до _END_ из _TOTAL_ записей",
            "infoEmpty": "Записи с 0 до 0 из 0 записей",
            "infoFiltered": "(отфильтровано из _MAX_ записей)",
            "infoPostFix": "",
            "loadingRecords": "Загрузка записей...",
            "zeroRecords": "Записи отсутствуют.",
            "emptyTable": "В таблице отсутствуют данные",
            "paginate": {
                "first": "Первая",
                "previous": "Предыдущая",
                "next": "Следующая",
                "last": "Последняя"
            }
        },
        "order": [[id, "asc"]],// 1 and 2 are buttons (usually...)
        "scrollX": true,
        "columnDefs": [
            { "width": "100px", "targets": 0 },
            { "width": "70px", "targets": 1 }
        ]
    });
}

/*
creates jquery calender widget
 */
function activateCalendar() {
    $('.date-calendar').datepicker(
        {
            dateFormat: 'dd.mm.yy',
            monthNames: ['Январь', 'Февраль', 'Март', 'Апрель',
                'Май', 'Июнь', 'Июль', 'Август', 'Сентябрь',
                'Октябрь', 'Ноябрь', 'Декабрь'],
            dayNamesMin: ['Вс', 'Пн', 'Вт', 'Ср', 'Чт', 'Пт', 'Сб'],
            firstDay: 1,
            changeYear: true
        });
}

/*
shows error dialog with some message.
params:
e - error as xhr.responseText.
errorText - message you wanna show in the dialog.
onClose - call back function that runs after the dialog closed.
 */
function showErrorMessage(errorText, title, onClose) {
    title = title || 'Ошибка';
    var errorDialog = $('#errorMessageDialog');
    errorDialog.find('div').text(errorText);
    errorDialog.dialog({
        show: {effect: "highlight", duration: 500},
        hide: {effect: "fade", duration: 500},
        title: title,
        modal: true,
        buttons: {
            Ok: function () {
                $(this).dialog("close");
            }
        },
        close: function( event, ui ) {
            if (onClose != null){
                onClose();
            }
        }
    });
}

/*
shows info dialog
 */
function showInfo ( text ) {
    var infoDialog = $('#infoDialog');
    infoDialog.find('div').text(text);
    infoDialog.dialog({
        show: {effect: "highlight", duration: 500},
        hide: {effect: "fade", duration: 500},
        title: 'Сообщение',
        modal: true,
        buttons: {
            Ok: function () {
                $(this).dialog("close");
            }
        }
    });
}

/*
shows load animation effect. in addition load effect shows only after 500 ms delay.
so, it happens only if response from server takes more then 500 ms.
i thing you don't wanna see blinks after each action you do. only if it takes long.
 */
var showLoadEffectClosed;
function
showLoadEffect(isActive) {
    var dialog = $('#loadDialog');
    if (isActive === true) {
        showLoadEffectClosed = false;
        setTimeout(function(){
            if (!showLoadEffectClosed)
                dialog.dialog('open');
        }, 500);
    } else {
        showLoadEffectClosed = true;
        dialog.dialog('close');
    }
}

/*
gets value from url string
params:
1.str - url string
2.isAlphabetic - if the url looks like this : 'http://localhost:8098/contract#' then you may not wanna have '#' in the result string.
being true lets the function get rid of symbols like this.
*/
function getValueFromUrl(str, isAlphabetic) {
    var afterSymStr = location.href.substr(location.href.lastIndexOf(str) + str.length);
    if (isAlphabetic === true){
        var indexOfNonCharSym = afterSymStr.search(/[^A-Za-z]/);
        return afterSymStr.substring(0, indexOfNonCharSym == -1 ? afterSymStr.length : indexOfNonCharSym);
    } else {
        return afterSymStr;
    }
}

/*
gets parameter from url
https://stackoverflow.com/questions/19491336/get-url-parameter-jquery-or-how-to-get-query-string-values-in-js
 */
function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
}

/*
makes parameterized a select object disabled and set it with value
*/
function disableSelect() {
    var param = getUrlParameter('idParam');
    if (param !== undefined){
        var selectPar = $('select.parameterized');
        selectPar.val(param);
        selectPar.prop('disabled', true);
    }
}

/*
shows dialog with linked dictionaries
 */
function showDictionaries(id) {
    //creates dictionary dialog
    // var paramStr = '?idParam=' + id;
    // var mfield = $('#dialog-dictionary-mfield');
    // var mfieldHref = mfield.attr('href');
    // mfield.attr('href', mfieldHref + '?idParam=' + id);
    //
    // var ufield = $('#dialog-dictionary-ufield');
    // var ufieldHref = ufield.attr('href');
    // ufield.attr('href', ufieldHref + '?idParam=' + id);
    //
    // var sfield = $('#dialog-dictionary-sfield');
    // var sfieldHref = sfield.attr('href');
    // sfield.attr('href', sfieldHref + '?idParam=' + id);
    //
    // var cfield = $('#dialog-dictionary-cfield');
    // var cfieldHref = cfield.attr('href');
    // cfield.attr('href', cfieldHref + '?idParam=' + id);
    //
    // var safield = $('#dialog-dictionary-safield');
    // var safieldHref = safield.attr('href');
    // safield.attr('href', safieldHref + '?idParam=' + id);

    addHrefAttr($('#dialog-dictionary-mfield'), id);
    addHrefAttr($('#dialog-dictionary-ufield'), id);
    addHrefAttr($('#dialog-dictionary-sfield'), id);
    addHrefAttr($('#dialog-dictionary-cfield'), id);
    addHrefAttr($('#dialog-dictionary-safield'), id);

    var dictionaryDialog = $('#dictionaryDialog');
    dictionaryDialog.removeClass('invisible');
    dictionaryDialog.dialog({
        autoOpen: true,
        show: {effect: "fade", duration: 400},
        hide: {effect: "fade", duration: 400},
        modal: true,
        width: '250'
    });
}

function addHrefAttr(obj, id) {
    var paramStr = '?idParam=' + id;
    var fieldHref = obj.attr('href');
    fieldHref = fieldHref.replace(paramStr, '');
    var hrefAttr = fieldHref + paramStr;
    obj.attr('href', hrefAttr);
}

/*
create standard file structures by filling dictionaries
 */
function createStandardStructure(settingId, templateId) {
    var confirm_dialog = $("#confirm-dialog");
    confirm_dialog.prop('title', 'Подтверждение');
    confirm_dialog.removeClass('invisible');
    $("#confirm-dialog-text").text('Справочники будут заполнены автоматически. Продолжить?');
    confirm_dialog.dialog({
        show: {effect: "drop", duration: 400},
        hide: {effect: "fold", duration: 400},
        resizable: false,
        height: "auto",
        width: "auto",
        modal: true,
        buttons: {
            Да: function () {
                $.ajax({
                    method: 'POST',
                    url: entity + '/setting/' + settingId + /template/ + templateId,
                    success: function () {
                        showLoadEffect(true);
                        showInfo('Справочники заполнены стандартными значениями.')
                    },
                    error:function (xhr) {
                        var err = JSON.parse(xhr.responseText);
                        showErrorMessage(err.message, 'Ошибка ' + err.status);
                    },
                    complete:function () {
                        showLoadEffect(false);
                    }
                });
                $(this).dialog('close');
            },
            Отмена: function () {
                $(this).dialog('close');
            }
        }
    });
}

/*
create template excel file as an example for base in file
*try with no jquery...
 */
function createExample() {
    showLoadEffect(true);
    var userSetting = $('#userSetting').val();
    var url = entity + "/example/"+userSetting;

    var xhr=new XMLHttpRequest();
    xhr.open("GET", url, true);
    xhr.responseType = 'arraybuffer';
    xhr.addEventListener('load',function(){
        if (xhr.status === 200){
            showLoadEffect(false);
            handleBlob(xhr.response, 'example.xlsx');
        }
    });
    xhr.send();
}

