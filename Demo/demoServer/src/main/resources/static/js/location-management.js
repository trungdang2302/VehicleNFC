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
    $('.clockpickerFrom').clockpicker({
        placement: 'bottom',
        align: 'left',
        donetext: 'Done',
        afterDone: function () {
            console.log("after done");
            parseTimeToLong("clockpickerFrom","ParkingFrom");
        }
    });
    $('.clockpickerTo').clockpicker({
        placement: 'bottom',
        align: 'left',
        donetext: 'Done',
        afterDone: function () {
            console.log("after done");
            parseTimeToLong("clockpickerTo","ParkingTo");
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
        row += '<td><a href="#" onclick="viewPolicy(' + content[i].id + ')" class="btn btn-primary viewBtn">View</a></td>';
        row += '<td><a href="#" onclick="createPolicy(' + content[i].id + ')" class="btn btn-primary viewBtn">Create Policy</a></td>';
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


function createPolicy(locationId) {
    var url = "http://localhost:8080/policy/create?locationId="+locationId;
    window.location.href = url;

    // $.ajax({
    //     type: "GET",
    //     dataType:"json",
    //     url: 'http://localhost:8080/vehicleType/get-all',
    //     success: function (data) {
    //         console.log("VehicleTypes: "+data);
    //         for ( i = 0; i < data.length; i++) {
    //             var chk = '<input type="checkbox" name="chk[]" id="vehicleType-' + i + '" value="'+ data[i].id +'">'+data[i].name+'';
    //             $('#vehicleTypeArr').append(chk);
    //         }
    //     }, error: function (data) {
    //         console.log("Could not load vehicle types")
    //     }
    // });
    //
    // $('#createPolicy').modal();


}

function viewPolicy(id) {
    // alert(id);
    $.ajax({
        type: "GET",
        dataType: "json",
        url: 'http://localhost:8080/location/get/' + id,
        success: function (data) {
            console.log(data);
            emptyTable();
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
    for (i = 0; i < content.length; i++) {
        // alert(content[i].id);
        emptyTable();
        emptyLi();
        $.ajax({
            type: "GET",
            dataType: "json",
            // contentType: "application/json",
            // data: JSON.stringify(content),
            url: 'http://localhost:8080/policy-vehicleType/get-vehicleTypes/' + content[i].id,
            success: function (data) {
                console.log("Policies: " + data);

                loadPolicy(data);
            }, error: function () {
                console.log("Could not load policy")
            }
        });
    }
}

function loadPolicy(data) {
    var row = "";
var pricings = "";
    for (i = 0; i < data.length; i++) {
        pricings = data[i].pricings;
        for ( j = 0; j < pricings.length; j++) {
            row = '<tr>';
            // row += '<td><input type="text" class="input index-'+ i +'-'+ j +'" name="vehicleType" disabled onkeypress="this.style.width = ((this.value.length + 1) * 8) + \'px\';"  value="' + data[i].policyId.allowedParkingFrom + '"></td>';
            row += '<td>'+data[i].vehicleTypeId.name+'</td>';
            row += '<td>' + data[i].policyId.allowedParkingFrom + '</td>';
            row += '<td>' + data[i].policyId.allowedParkingTo + '</td>';
            row += '<td>' + pricings[j].fromHour + '</td>';
            row += '<td>' + pricings[j].pricePerHour + '</td>';
            row += '<td>' + pricings[j].lateFeePerHour + '</td>';
            row += '<td> <button class="editBtn" onclick="Edit('+ data[i].policyId.id +', ' + data[i].vehicleTypeId.id + ', '+ j +')">Edit</button>';
            row += '<td> <button class="saveBtn" onclick="Save('+ pricings[j].id +', ' + i + ', '+ j +')">Save</button>';
            row += '</tr>';
            $('#policy-table tbody').append(row);
        }
    }
    // var length = $('.input').val().length;
    // $('.input').css('width',(length * 8) + 'px');
    // $('.saveBtn').hide();
}

function Edit(policyId, vehicleTypeId, pricingIndex ) {

    // $('.index-'+policyIndex+'-'+pricingIndex).prop('disabled', false);
    $('.saveBtn').show();
    $('.editBtn').hide();
    var url = "http://localhost:8080/policy/edit?policyId="+policyId+"&vehicleTypeId="+vehicleTypeId;
    window.location.href = url;

}
function parseTimeToLong(clockPicker, type) {
    console.log(type);
    console.log("log: "+$('.clockpickerFrom #ParkingFrom').val());
    var time = $('.'+clockPicker+' #'+type).val();
    console.log("Time: "+time);
    var temp = time.split(":")
    var hour = temp[0];
    console.log("hour: " + hour);
    var minute = temp[1];
    console.log("Minute: "+minute);
    console.log("hour ms: "+parseInt(hour * 3600000));
    console.log("minute ms: "+parseInt(minute * 60000));
    var ms = parseInt(hour * 3600000) + parseInt(minute * 60000);
    console.log(ms);
    $('#allowed'+type).val(ms);
}