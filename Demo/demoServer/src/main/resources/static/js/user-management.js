$(document).ready(function (e) {

    $.ajax({
        type: "GET",
        dataType: "json",
        url: 'http://localhost:8080/user/get-users-json',
        success: function (data) {
            console.log(data);
            loadData(data);
        }, error: function () {
            alert("Can't load data")
        }

    });

    submitDeleteUserForm();
    submitCreateUserForm();
    // save user form
    var frm = $('#save-user-form');
    frm.submit(function (e) {
        e.preventDefault();

        $.ajax({
            type: frm.attr('method'),
            url: frm.attr('action'),
            data: frm.serialize(),
            success: function (data) {
                console.log("Save successfully.");
                $('.myForm #exampleModal').modal('hide');
                console.log(data);
                location.reload(true);
            }, error: function (data) {
                console.log("Could not save user");
                console.log(data);
            }
        });
    });
});

function submitDeleteUserForm() {
    var deleteFrm = $('#delete-form');
    deleteFrm.submit(function (e) {
        var id = $('#deleteModal #id').val();
        alert(id);
        e.preventDefault();
        $.ajax({
            type: deleteFrm.attr('method'),
            url: deleteFrm.attr('action')+'/'+id,
            url: 'http://localhost:8080/user/delete-user/'+id,
            data: deleteFrm.serialize(),
            success: function (data) {
                console.log(data);
                console.log("Delete user successfully!");
                $('#deleteModal').modal('hide');
                location.reload(true);
            }, error: function (data) {
                console.log(data);
                console.log("Could not delete user!");
            }
        });
    });
}

function submitCreateUserForm() {

    var userFrm = $('#create-user-form');
    userFrm.submit(function (e) {
       e.preventDefault();
        var vehicleType = $('#vehicle-list option:selected').val();
        console.log("vehicle: "+vehicleType);
        $('.user-form #vehicleTypeId').val(vehicleType);
       $.ajax({
           type: userFrm.attr('method'),
           url: userFrm.attr('action'),
           data: userFrm.serialize(),
           success: function (data) {
               console.log("Create Successfully");
               $('.user-form #createUserModal').modal('hide');
                $('#show-userID-modal .hashed-id').text(data);
                $('#show-userID-modal').modal();

           }, error: function () {
               console.log("Could not create this user");
           }
       });

    });
}
function loadUserModal(id) {
    $.ajax({
        type: "GET",
        dataType: "json",
        url: 'http://localhost:8080/user/getUser/' + id,
        success: function (user) {
            // var user = JSON.parse(data);
            console.log("user: " + user);
            $('.myForm #id').val(user.id);
            $('.myForm #phoneNumber').val(user.phoneNumber);
            $('.myForm #firstName').val(user.firstName);
            $('.myForm #lastName').val(user.lastName);
            $('.myForm #vehicleNumber').val(user.vehicleNumber);
            $('.myForm #licensePlateId').val(user.licensePlateId);
            $('.myForm #vehicleTypeName').val(user.vehicleTypeId.name);
            $('.myForm #vehicleTypeId').val(user.vehicleTypeId.id);
        }, error: function () {
            console.log("Could not load data");
        }
    });
    $('.myForm #exampleModal').modal();
}

function loadCreateModal() {
        $.ajax({
            type: "GET",
            dataType: "json",
            url: 'http://localhost:8080/vehicleType/get-all',
            success: function (data) {
                console.log("VehicleList: " + data.length);
                var i;
                var option = "";
                $('#vehicle-list option').remove();
                for (i = 0; i < data.length; i++) {
                    console.log("Times: " + i)
                    option = '<option value="' + data[i].id + '">' + data[i].name + '</option>';
                    $('#vehicle-list').append(option);
                    option = "";
                }

            }, error: function (data) {
                console.log("Could not load data");
            }
        });
        $('.user-form #createUserModal').modal();
}

function deleteModal(id) {
    // var url = "delete-user/" + id;
    $('.delBtn').on('click', function (event) {
        event.preventDefault();
        $('#deleteModal #id').val(id);

        $('#deleteModal').modal();
    });
}

function emptyTable() {
    $('#user-table td').remove();
}

function emptyPaginationLi() {
    $('#pagination').empty();
}

function loadData(data) {
    var content = "";
    content = data.content;
    var row = "";
    for (i = 0; i < content.length; i++) {
        row = '<tr>';
        // row += '<td>' + content[i].id + '</td>';
        row += '<td>' + content[i].phoneNumber + '</td>';
        // row += '<td>' + content[i].password + '</td>';
        row += '<td>' + content[i].money + '</td>';
        row += '<td>' + content[i].firstName + '</td>';
        row += '<td>' + content[i].lastName + '</td>';
        row += '<td>' + content[i].vehicleNumber + '</td>';
        row += '<td>' + content[i].licensePlateId + '</td>';
        row += '<td>' + content[i].vehicleTypeId.name + '</td>';
        row += '<td><a href="#" onclick="loadUserModal(' + content[i].id + ')" class="btn btn-primary edtBtn">Edit</a></td>';
        row += '<td><a href="#" onclick="deleteModal(' + content[i].id + ')" class="btn btn-danger delBtn">Delete</a></td>'
        row += '</tr>';
        $('#user-table tbody').append(row);
    }

    var pageNumber = data.pageable.pageNumber;
    console.log("page: " + pageNumber);
    console.log("Total Page: " + data.totalPages);
    var currentPage;
    var li = "";
    for (currentPage = 0; currentPage <= data.totalPages - 1; currentPage++) {
        if (currentPage === pageNumber) {
            li = '<li class="nav-item active">\n' +
                '<a href="#" class="nav-link" onclick="getUserByPageNumber(' + currentPage + ')">' + currentPage + '</a>\n' +
                '</li>';
            $('#pagination').append(li);
        } else {

            li = '<li class="nav-item">\n' +
                '<a href="#" class="nav-link" onclick="getUserByPageNumber(' + currentPage + ')">\n' +
                +currentPage + '</a>\n' +
                '</li>';
            $('#pagination').append(li);
        }
    }
}

function getUserByPageNumber(pageNumber) {
    $.ajax({
        type: "GET",
        dataType: "json",
        url: 'http://localhost:8080/user/get-users-json?page=' + pageNumber,
        success: function (data) {
            console.log(data);
            emptyTable();
            emptyPaginationLi();
            loadData(data);
        }, error: function () {
            alert("Can't load data")
        }
    });
}
$(document).ready(function (e){
    // Sort table headers
    $('th').click(function(){
        var table = $(this).parents('table').eq(0)
        var rows = table.find('tr:gt(0)').toArray().sort(comparer($(this).index()))
        this.asc = !this.asc
        if (!this.asc){rows = rows.reverse()}
        for (var i = 0; i < rows.length; i++){table.append(rows[i])}
    })
    function comparer(index) {
        return function(a, b) {
            var valA = getCellValue(a, index), valB = getCellValue(b, index)
            return $.isNumeric(valA) && $.isNumeric(valB) ? valA - valB : valA.toString().localeCompare(valB)
        }
    }
    function getCellValue(row, index){ return $(row).children('td').eq(index).text() }
    // end sort table headers
});

