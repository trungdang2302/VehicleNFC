$(document).ready(function (e) {
    $.ajax({
        type: "GET",
        dataType: "json",
        url: 'http://localhost:8080/location/get-locations',
        success: function (data) {
            console.log(data);
            loadData(data);
        }, error: function () {
            alert("Can't load data")
        }

    });
});
function emptyTable() {
    $('#policy-table td').remove();
}
function emptyLi() {
    $('#policy-table li').empty();
}
function loadData(res) {
    var content = "";
    content = res.data;
    var row = "";
    for (i = 0; i < content.length; i++) {
        var status = "";
        if (content[i].activated === true) {
            status = "Active";
        } else {
            status = "De-active"
        }
        row = '<tr>';
        row += '<td>' + content[i].id + '</td>';
        row += '<td>' + content[i].location + '</td>';
        row += '<td>' + content[i].description + '</td>';
        row += '<td>' + status + '</td>';
        row += '<td><a href="#" onclick="viewPolicy(' + content[i].id + ')" class="btn btn-primary viewBtn">Viewt</a></td>';
        row += '</tr>';
        $('#location-table tbody').append(row);
    }

    var pageNumber = res.pageNumber;
    console.log("page: " + pageNumber);
    console.log("Total Page: " + res.totalPages);
    var currentPage;
    var li = "";
    for (currentPage = 0; currentPage <= res.totalPages - 1; currentPage++) {
        if (currentPage === pageNumber) {
            li = '<li class="nav-item active">\n' +
                '<a href="#" class="nav-link" onclick="searchUser(' + currentPage + ')">' + currentPage + '</a>\n' +
                '</li>';
            $('#pagination').append(li);
        } else {

            li = '<li class="nav-item">\n' +
                '<a href="#" class="nav-link" onclick="searchUser(' + currentPage + ')">\n' +
                +currentPage + '</a>\n' +
                '</li>';
            $('#pagination').append(li);
        }
    }
}

function viewPolicy(id) {

    $.ajax({
        type: "GET",
        dataType: "json",
        url: 'http://localhost:8080/location/get/' + id,
        success: function (data) {
            console.log(data);
            emptyLi();
            loadPolicyTable(data);

        }, error: function () {
            alert("Can't load data")
        }

    });
    $('#policyModal').modal();
}

function loadPolicyTable(data) {

    console.log(data);
    var content = "";
    content = data.policyList;
    $.ajax({
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(content),
        url: 'http://localhost:8080/policy-vehicleType/get-vehicleTypes-byPolicies/',
        success: function(data) {
            console.log("Policies: "+data);
            loadPolicy(data);
        }, error:function () {
            console.log("Could not load policy")
        }
    });
}

function loadPolicy(policyID) {

    var row ="";
    for (i = 0; i < content.length; i++) {
        row = '<tr>';
        row += '<td>' + content[i].id + '</td>';
        row += '<td>' + content[i].location + '</td>';
        row += '<td>' + content[i].description + '</td>';
        row += '<td>' + status + '</td>';
        row += '<td><a href="#" onclick="viewPolicy(' + content[i].id + ')" class="btn btn-primary viewBtn">Viewt</a></td>';
        row += '</tr>';
        $('#location-table tbody').append(row);
    }
}