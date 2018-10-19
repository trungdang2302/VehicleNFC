$(document).ready(function () {
    var policyHasVehicleType = "";
    var url = location.href;
    console.log("URL: " + url);
    var index = url.lastIndexOf("?");
    var params = url.slice(index, url.length);
    console.log("params: " + params);
    var paramArr = params.split("&");

    var policyIdStr = paramArr[0];
    console.log("POlicyStr: " + policyIdStr);
    var policyId = policyIdStr.slice(policyIdStr.indexOf("=") + 1, policyIdStr.length);
    console.log("POlicyId: " + policyId);

    var vehicleTypeIdStr = paramArr[1];
    var vehicleTypeId = vehicleTypeIdStr.slice(vehicleTypeIdStr.indexOf("=") + 1, vehicleTypeIdStr.indexOf("=") + 2);
    console.log(vehicleTypeId);
    // parseTimeToLong();
    loadPolicy(policyId, vehicleTypeId);
    addPricing();
    savePolicyHasVehicleType();
    deletePolicy();
    $('.clockpickerFrom').clockpicker({
        placement: 'bottom',
        align: 'left',
        donetext: 'Done',
        afterDone: function () {
            console.log("after done");
            parseTimeToLong("clockpickerFrom", "ParkingFrom");
        }
    });
    $('.clockpickerTo').clockpicker({
        placement: 'bottom',
        align: 'left',
        donetext: 'Done',
        afterDone: function () {
            console.log("after done");
            parseTimeToLong("clockpickerTo", "ParkingTo");
        }
    });
});

function loadPolicy(policyId, vehicleTypeId) {
    $.ajax({
        type: "GET",
        dataType: "json",
        url: 'http://localhost:8080/policy-vehicleType/get-by-policy-vehicleType?policyId=' + policyId + '&vehicleTypeId=' + vehicleTypeId,
        success: function (res) {
            console.log(res);
            loadData(res);
        }, error: function (res) {
            console.log(res);
        }
    });
}

function loadData(data) {
    $('#vehicleType').val(data.vehicleTypeId.name);
    $('#ParkingFrom').val(data.policyId.allowedParkingFrom);
    $('#ParkingTo').val(data.policyId.allowedParkingTo);

    // hidden inputs
    $('#policyHasTblVehicleTypeId').val(data.id);
    policyHasVehicleType = data.id;
    $('#policyId').val(data.policyId.id);
    $('#vehicleTypeId').val(data.vehicleTypeId.id);

    var pricings = data.pricings;
    for (i = 0; i < pricings.length; i++) {
        var row = '<tr>';
        row += '<td><input type="text" class="input" name="vehicleType"  value="' + pricings[i].fromHour + '"></td>';
        row += '<td><input type="text" class="input" name="vehicleType"  value="' + pricings[i].pricePerHour + '"></td>';
        row += '<td><input type="text" class="input" name="vehicleType"  value="' + pricings[i].lateFeePerHour + '"></td>';
        row += '<td><a href="#" onclick="savePricing(' + data.id + ' , ' + pricings[i].id + ')" class="btn btn-primary saveBtn">Edit</a></td>'
        row += '<td><a href="#" onclick="deleteModal(' + pricings[i].id + ')" class="btn btn-danger delBtn">Delete</a></td>'
        row += '</tr>';
        $('#orderPricings tbody').append(row);
    }
}

function savePricing(policyHasVehicleTypeId, pricingId) {

    $('#updatePricingModal').modal();
    var updateFrm = $('#update-pricing');
    $('#pricingId').val(pricingId);
    $('.update-pricing-form #policyHasTblVehicleTypeId').val(policyHasVehicleTypeId);
    updateFrm.submit(function (e) {
        e.preventDefault();
        $.ajax({
            type: updateFrm.attr('method'),
            url: updateFrm.attr('action'),
            data: updateFrm.serialize(),
            success: (res) => {
                console.log(res);
                console.log("Update Successfully");
                $('#updatePricingModal').modal('hide');
            }, error: (res) => {
                console.log("Failed to save");
                console.log(res);
            }
        });
    });
}

function addPricing() {
    $('#btn-add-pricing').on('click', function (e) {
        $('#savePricingModal').modal();
        var frm = $('#save-pricing');
        frm.submit(function (e) {
            e.preventDefault();
            $.ajax({
                type: frm.attr('method'),
                url: frm.attr('action'),
                data: frm.serialize(),
                success: function (data) {
                    console.log("Add Successfully");
                    console.log(data);
                    $('#savePricingModal').modal('hide');
                    location.reload(true);
                }, error: function (data) {
                    console.log("Failed to save");
                    console.log(data);
                }
            });
        });

    });
}

function deleteModal(pricingId) {
    $('#deleteModal').modal();
    var frm = $('#delete-form');
    frm.submit(function (e) {
        e.preventDefault();
        $.ajax({
            type: frm.attr('method'),
            url: frm.attr('action') + "/" + pricingId,
            success: function (res) {
                console.log(res);
                $('#deleteModal').modal('hide');
                location.reload(true);
            }, error: function (res) {
                console.log("Failed to delete");
            }
        });
    });
}

function savePolicyHasVehicleType() {
    $('#save-policy').on('click', function (e) {
        var json = createPolicyHasVehicleTypeJson();
        console.log("JSON: " + json);
        $.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8",
            // dataType: "json",
            url: "http://localhost:8080/policy-vehicleType/save",
            data: JSON.stringify(json),
            success: function (res) {
                console.log(res);
                console.log("Save successfully");
                location.reload(true);
            }, error: function (res) {
                console.log(res);
                console.log("Failed to save");
            }
        });
    });
}

function deletePolicy() {
    $('#delete-policy').on('click', function (e) {
        var json = createPolicyHasVehicleTypeJson();
        console.log("JSON: " + json);
        $.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8",
            // dataType: "json",
            url: "http://localhost:8080/policy-vehicleType/delete",
            data: JSON.stringify(json),
            success: function (res) {
                console.log(res);
                console.log("Save successfully");
                window.location.href = "http://localhost:8080/location/page";
            }, error: function (res) {
                console.log(res);
                console.log("Failed to save");
            }
        });
    });
}

function createPolicyHasVehicleTypeJson() {
    var vehicleType = {
        id: $('#vehicleTypeId').val(),
        name: $('#vehicleType').val(),
    }
    var policy = {
        id: $('#policyId').val(),
        allowedParkingFrom: $('#allowedParkingFrom').val(),
        allowedParkingTo: $('#allowedParkingTo').val(),
    }
    var policyHasVehicleTypeJson = {
        id: $('#policyHasTblVehicleTypeId').val(),
        policyId: policy,
        vehicleTypeId: vehicleType,
    }
    return policyHasVehicleTypeJson;
}

function parseTimeToLong(clockPicker, type) {
    console.log(type);
    console.log("log: " + $('.clockpickerFrom #ParkingFrom').val());
    var time = $('.' + clockPicker + ' #' + type).val();
    console.log("Time: " + time);
    var temp = time.split(":")
    var hour = temp[0];
    console.log("hour: " + hour);
    var minute = temp[1];
    console.log("Minute: " + minute);
    console.log("hour ms: " + parseInt(hour * 3600000));
    console.log("minute ms: " + parseInt(minute * 60000));
    var ms = parseInt(hour * 3600000) + parseInt(minute * 60000);
    console.log(ms);
    $('#allowed' + type).val(ms);
}