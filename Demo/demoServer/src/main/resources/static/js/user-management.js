$(document).ready(function (e) {
    var phone = $('#main-content', window.parent.document).attr('phone');
    if (typeof(phone) === 'undefined') {
        $.ajax({
            type: "GET",
            dataType: "json",
            url: 'get-users-json',
            success: function (data) {
                console.log(data);
                loadData(data);
            }, error: function () {
                alert("Can't load data")
            }

        });
    } else {
        $('#searchValue').val(phone);
        searchUser(0);
    }
});

function emptyTable() {
    $('#user-table td').remove();
}

function emptyPaginationLi() {
    $('#pagination').empty();
}

function loadData(res) {
    var content = "";
    content = res.data;
    var row = "";
    for (i = 0; i < content.length; i++) {
        row = '<tr>';
        // row += '<td>' + content[i].id + '</td>';
        row += cellBuilder((i + (res.pageNumber * res.pageSize) + 1), "text-center");
        row += '<td class="text-right">' + content[i].phoneNumber + '</td>';
        // row += '<td>' + content[i].password + '</td>';
        row += '<td>' + content[i].firstName + ' ' + content[i].lastName + '</td>';
        row += '<td class="text-right">' + (content[i].money * 1000).toLocaleString() + " vnđ" + '</td>';
        row += '<td class="text-right">' + content[i].vehicle.vehicleNumber + '</td>';
        var vehicleType = (content[i].vehicle.vehicleTypeId != null)
            ? content[i].vehicle.vehicleTypeId.name : "Empty";
        row += '<td class="text-center">' + vehicleType + '</td>';
        // row += '<td>' + content[i].vehicleTypeId.name + '</td>';
        var edit = "<a href=\"#\" onclick=\"loadUserInfo('" + content[i].id + "')\" class=\"btn btn-primary btnAction\"><i class=\"lnr lnr-pencil\"></i></a>";
        var deleteStr = "<a href=\"#\" onclick=\"openDeleteModal('" + content[i].id + "')\" class=\"btn btn-danger btnAction-remove\"><i class=\"lnr lnr-trash\"></i></a>";
        row += cellBuilder(deleteStr + edit);
        row += '</tr>';
        $('#user-table tbody').append(row);
    }

    var pageNumber = res.pageNumber;
    console.log("page: " + pageNumber);
    console.log("Total Page: " + res.totalPages);
    var currentPage;
    var li = "";
    for (currentPage = 0; currentPage < res.totalPages; currentPage++) {
        if (currentPage === pageNumber) {
            li = '<li class="nav-item active">\n' +
                '<a href="#" class="nav-link" onclick="searchUser(' + currentPage + ')">' + (currentPage + 1) + '</a>\n' +
                '</li>';
            $('#pagination').append(li);
        } else {

            li = '<li class="nav-item">\n' +
                '<a href="#" class="nav-link" onclick="searchUser(' + currentPage + ')">\n' +
                +(currentPage + 1) + '</a>\n' +
                '</li>';
            $('#pagination').append(li);
        }
    }
}

$(document).ready(function (e) {
    // Sort table headers
    $('th').click(function () {
        var table = $(this).parents('table').eq(0)
        var rows = table.find('tr:gt(0)').toArray().sort(comparer($(this).index()))
        this.asc = !this.asc
        if (!this.asc) {
            rows = rows.reverse()
        }
        for (var i = 0; i < rows.length; i++) {
            table.append(rows[i])
        }
    })

    function comparer(index) {
        return function (a, b) {
            var valA = getCellValue(a, index), valB = getCellValue(b, index)
            return $.isNumeric(valA) && $.isNumeric(valB) ? valA - valB : valA.toString().localeCompare(valB)
        }
    }

    function getCellValue(row, index) {
        return $(row).children('td').eq(index).text()
    }

    // end sort table headers
});

$(document).ready(function (e) {

    $('#searchBtn').on('click', function (e) {
        e.preventDefault();
        searchUser(0);
    });
});

function searchUser(pageNumber) {
    var url = "search-user";
    if (pageNumber != null) {
        url = url + "?page=" + pageNumber;
    }
    var listFilterObject = [];
    var vehicleType = $('#search-filter option:selected').val();
    var searchValue = $('#searchValue').val();

    console.log("Search By: " + vehicleType);
    console.log("SearchValue: " + searchValue);
    var filterObject = createSearchObject(vehicleType, ":", searchValue);
    listFilterObject.push(filterObject);
    $.ajax({
        type: 'POST',
        url: url,
        dataType: "json",
        contentType: 'application/json',
        data: JSON.stringify(listFilterObject),
        success: function (response) {
            emptyTable();
            emptyPaginationLi();
            loadData(response.data);
            console.log(response);
        }
    });
}

function createSearchObject(key, operation, value) {
    var obj = {
        key: key,
        operation: operation,
        value: value
    };
    return obj;
}

function cellBuilder(text, className) {
    text = (text != null) ? text : "Empty";
    return "<td class='" + className + "'>" + text + "</td>";
}

function loadVehicleInfo(vehicleNumber) {
    $('#main-content-user-list').hide();
    $('#main-content-verify-form').show();
    $.ajax({
        type: "GET",
        dataType: "json",
        url: 'vehicle/get-vehicle/' + vehicleNumber,
        success: function (data) {
            setUpFormData(data);
        }, error: function () {
            alert("Can't load data")
        }
    });
    //load vehicle Type
    $.ajax({
        type: "GET",
        dataType: "json",
        url: '/vehicle-type/get-all',
        success: function (data) {
            setUpVehicleType(data, 'verify-vehicle-list')
        }, error: function () {
            alert("Can't load data")
        }

    });
}


function setUpFormData(vehicle) {
    $('#vehicleNumberShow').val(vehicle.vehicleNumber);
    $('#verify-vehicleNumber').val(vehicle.vehicleNumber);
    $('#licenseIdShow').val(vehicle.licensePlateId);
    $('#licenseId').val(vehicle.licensePlateId);
}

function setUpVehicleType(list, holder) {
    $('#' + holder).empty();
    for (var i = 0; i < list.length; i++) {
        var option = "<option value='" + list[i].id + "'>" + list[i].name + "</option>";
        $('#' + holder).append(option);
    }
}


function setLongFromExpireDate() {
    var time = $('#datepicker').val().split("-");
    var date = new Date(time[1] + "-" + time[0] + "-" + time[2]);
    $('#expireDate').val(date.getTime());
}


$('#datepicker').datepicker({
    weekStart: 1,
    autoclose: true,
    format: "dd-mm-yyyy",
    todayHighlight: true,
});

$('#verify-vehicle-form').on('submit', function (e) {
    $.ajax({
        type: 'post',
        url: '/vehicle/verify-vehicle',
        data: $('#verify-vehicle-form').serialize(),
        success: function (data) {
            if (data) {
                location.reload();
            }
        }
    });
    e.preventDefault();
});

function closeForm() {
    $('#verify-vehicle-form').trigger("reset");
    $('#main-content-user-list').show();
    $('#main-content-verify-form').hide();
    $('#main-content-save-form').hide();
}

function openSaveForm() {

    $('#main-content-user-list').hide();
    $('#main-content-save-form').show();
}

$('#save-user-form').on('submit', function (e) {
    if ($('#id').val() === '') {
        $('#id').removeAttr('value');
    }
    e.preventDefault();
    $.ajax({
        type: 'post',
        url: 'create-user',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: buildUserJSON(),
        success: function (data) {
            location.reload();
        }, error: function (data) {
            console.log(data);
        }
    });
});

function buildUserJSON() {
    var string = {
        id: $('#id').val(),
        phoneNumber: $('#phoneNumber').val(),
        firstName: $('#firstName').val(),
        lastName: $('#lastName').val(),
        password: $('#password').val(),
        vehicle: {
            "vehicleNumber": $('#vehicleNumber').val(),
            "licensePlateId": $('#licensePlateId').val()
        }
    };
    return JSON.stringify(string);
}

function loadUserInfo(id) {
    openSaveForm();
    $.ajax({
        type: "GET",
        dataType: "json",
        url: 'get-user/' + id,
        success: function (data) {
            setUpUserInfo(data);
        }, error: function () {
            alert("Can't load data")
        }
    });
}

function setUpUserInfo(data) {
    // alert(data.phoneNumber);
    $('#id').val(data.id);
    $('#phoneNumber').val(data.phoneNumber);
    $('#firstName').val(data.firstName);
    $('#lastName').val(data.lastName);
    $('#password').val(data.password);
    $('#vehicleNumber').val(data.vehicle.vehicleNumber);
    $('#licensePlateId').val(data.vehicle.licensePlateId);
}


function openDeleteModal(vehicleNumber) {
    $('#deleteModal').modal(focus);
    $('#delete-id').val(vehicleNumber);
}

function deleteUser(id) {
    $.post("delete-user",
        {
            id: id,
        },
        (function (data, status) {
            if (data) {
                location.reload();
            }
        }));
}