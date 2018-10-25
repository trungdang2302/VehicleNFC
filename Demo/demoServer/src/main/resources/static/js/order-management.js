$(document).ready(function () {
    // initOrders();
    sortHeaders();
    $('#datepicker').datepicker({
        weekStart: 1,
        autoclose: true,
        format: "dd-mm-yyyy",
        todayHighlight: true,
    });
    // $('#order-table').attr('display','none');
    // $('#order-table').hide();
    // var picker = $('#datepicker');

    // picker.on('changeDate', function(e) {
    //     console.log($('#datepicker').val());
    // });
    $('#searchBtn').on('click', function (e) {
        e.preventDefault();

        $('#search-date').val($('#datepicker').val());
        var searchValue =  $('#searchValue').val();
        $('#search-value').val(searchValue);
        filterOrder(0);
    });

    // $('.clockpicker').clockpicker({
    //     placement: 'bottom',
    //     align: 'left',
    //     donetext: 'Done',
    //     // afterDone: function () {
    //     //     console.log("after done");
    //     //     parseTimeToLong("clockpickerFrom", "ParkingFrom");
    //     // }
    // });
});
function initOrders() {
    $.ajax({
        type: "GET",
        dataType: "json",
        url: 'http://localhost:8080/order/get-orders',
        success: function (data) {
            console.log(data);
            $('#order-table').attr('display','block');
            loadData(data);
        }, error: function () {
            alert("Can't load data")
        }
    });
}
function loadData(res) {
    var content = "";
    content = res.data;
    $('#order-table').show();
    if (content.length != 0) {

        var row = "";
        for (i = 0; i < content.length; i++) {
            row = '<tr>';
            row += '<td>' + content[i].id + '</td>';
            row += '<td>' + convertDate(content[i].checkInDate) + '</td>';
            row += '<td>' + convertDate(content[i].checkOutDate) + '</td>';
            row += '<td>' + content[i].locationId.location + '</td>';
            if (content[i].orderStatusId.name === "close") {
                row += '<td style=" color: #ff0000;">' + content[i].orderStatusId.name + '</td>';
            } else {
                row += '<td style="color: #339933;">' + content[i].orderStatusId.name + '</td>';
            }

            // row += '<td>' + content[i].userId.firstName+' '+ content[i].userId.lastName + '</td>';
            // row += '<td>' + content[i].userId.phoneNumber + '</td>';
            row += '<td>' + content[i].userId.vehicleNumber + '</td>';
            row += '<td>' + content[i].userId.vehicle.licensePlateId + '</td>';
            var duration = "";
            if (content[i].duration === null) {
                duration = 0;
            } else {
                duration = content[i].duration;
            }
            row += '<td>' + msToTime(duration) + '</td>';
            var total = "";
            if (content[i].total === null) {
                total = 0;
            } else {
                total = content[i].total;
            }
            row += '<td>' + total + '</td>';
            row += '<td><a href="#" onclick="viewPricingDetail(' + content[i].id + ')" class="btn btn-primary viewBtn">View Detail</a></td>'
            row += '</tr>';
            $('#order-table tbody').append(row);
        }

        var pageNumber = res.pageNumber;
        console.log("page: " + pageNumber);
        console.log("Total Page: " + res.totalPages);
        var currentPage;
        var li = "";
        for (currentPage = 0; currentPage <= res.totalPages - 1; currentPage++) {
            if (currentPage === pageNumber) {
                li = '<li class="nav-item active">\n' +
                    '<a href="#" class="nav-link" onclick="filterOrder(' + currentPage + ')">' + (currentPage+ 1) + '</a>\n' +
                    '</li>';
                $('#pagination').append(li);
            } else {

                li = '<li class="nav-item">\n' +
                    '<a href="#" class="nav-link" onclick="filterOrder(' + currentPage + ')">\n' +
                    +(currentPage+1) + '</a>\n' +
                    '</li>';
                $('#pagination').append(li);
            }
        }
    } else {
        var row = '<tr>No data</tr>'
        $('#order-table tbody').append(row);
    }

}

function filterOrder(pageNumber) {
    var url = "http://localhost:8080/order/filter-order";
    if (pageNumber != null) {
        url = url+"?page="+pageNumber;
    }
    var searchType = $('#search-filter option:selected').val();
    var searchValue = $('#search-value').val();
    var checkInDate = convertDateToMs($('#search-date').val());
    console.log(checkInDate);
    var timeType = 'checkInDate';
    console.log("Search By: "+searchType);
    console.log("SearchValue: "+searchValue);
    var listFilterObject = [];
    var searchValue = createSearchObject(searchType, ":", searchValue);
    var searchTime = createSearchObject(timeType,":", checkInDate);
    listFilterObject.push(searchValue);
    listFilterObject.push(searchTime);
    $.ajax({
        type:'POST',
        url: url,
        dataType:"json",
        contentType: 'application/json',
        data: JSON.stringify(listFilterObject),
        success:function(response){

            emptyTable();
            emptyPaginationLi();
            loadData(response);
            console.log(response);
        }, error: function (res) {
            console.log(res);
        }
    });
}
function getOrderById(id) {
    var obj = "";
    $.ajax({
       type: "GET",
       dataType: "json",
        sync: false,
       url:  'http://localhost:8080/order/get-order/'+id,
        success:function (res) {
           console.log("Order:"+res.id)
            obj = res;

        }, error: function (data) {
            console.log("Could not load Order")
            console.log(data);
        }
    });
    return obj;
}
function viewPricingDetail(orderId) {
    var order = $.getValues("http://localhost:8080/order/get-order/"+orderId);
    console.log(order);
    $.ajax({
        type: "GET",
        dataType: "json",
        url: 'http://localhost:8080/order-pricing/get/' + orderId,
        success: function (res) {
            // console.log(res);
            // $('.myForm #lastName').text(order.userId.firstName+' '+ order.userId.lastName);
            $('.myForm #phoneNumber').text(order.userId.phoneNumber);
            $('.myForm #location').text(order.locationId.location);
            $('.myForm #allowedParkingFrom').text(convertTime(order.allowedParkingFrom));
            $('.myForm #allowedParkingTo').text(convertTime(order.allowedParkingTo));
            $('.myForm #checkInDate').text(convertDate(order.checkInDate));
            $('.myForm #checkOutDate').text(convertDate(order.checkOutDate));
            let row = "";
            emptyPricingTable();
            console.log("Pricing SIze: "+res.length);
            var hourHasPrices = order.hourHasPrices;
            var passHour = 0;
            var minutes = hourHasPrices[hourHasPrices.length-1].minutes;
            for ( i = 0; i < hourHasPrices.length; i++) {
                // table as receipt
                let hourHasPrice = hourHasPrices[i];
                let fromHour ="";
                if (i === 0) {
                    passHour = hourHasPrice.hour;
                    fromHour =  "First " +hourHasPrice.hour + " Hour";
                } else if (i === hourHasPrices.length - 1) {
                    hourHasPrice.hour = hourHasPrice.hour - passHour;
                    let minutePass = hourHasPrice.minutes + " Minute";
                    fromHour = " After "+ hourHasPrice.hour + " Hour " + minutePass;
                    hourHasPrice.total = (hourHasPrice.total + ((parseFloat(minutes / 60)) * (hourHasPrice.price)))
                } else {
                    fromHour = " From " + passHour + " Hour To " + hourHasPrice.hour + " Hour";
                    passHour = hourHasPrice.hour;
                }
                row = '<tr>';
                row += '<td>' + fromHour + '</td>';
                row += '<td>' + hourHasPrice.price + '</td>';
                row += '<td>' + hourHasPrice.total + '</td>';
                row += '</tr>';


                // table for order pricing
                // row = '<tr>';
                // row += '<td>' + res[i].fromHour + '</td>';
                // row += '<td>' + res[i].pricePerHour + '</td>';
                // row += '<td>' + (res[i].fromHour * res[i].pricePerHour) + '</td>';
                // row += '<td>' + res[i].lateFeePerHour + '</td>';
                // row += '</tr>';
                $('#orderPricings tbody').append(row);
            }

            var duration = "";
            if (order.duration === null) {
                duration = 0;
            } else {
                duration = order.duration;
            }

            $('.myForm #duration').text(msToTime(duration));
            var total = "";
            if (order.total === null) {
                total = 0;
            } else {
                total = order.total;
            }
            let rowTotal = '<tr><td></td><td><label>Total: </label></td><td><label>' + total + ' .000d-</label></td><td></td></tr>';
            $('#orderPricings tbody').append(rowTotal);
            $('.myForm #total').text(total);
            // $('.myForm #vehicleTypeId').text(order.userId.vehicleTypeId.name);
        }, error: function () {
            console.log("Could not load data");
        }
    });
    $('.myForm #OrderDetailModal').modal();
}
jQuery.extend({
    getValues: function(url) {
        var result = null;
        $.ajax({
            url: url,
            type: 'get',
            dataType: 'json',
            async: false,
            success: function(data) {
                result = data;
            }
        });
        return result;
    }
});
function emptyTable() {
    $('#order-table td').remove();
}

function emptyPricingTable() {
    $('#orderPricings td').remove();
}
function emptyPaginationLi() {
    $('#pagination').empty();
}
function createSearchObject(key, operation, value) {
    var obj = {
        key: key,
        operation: operation,
        value: value
    };
    return obj;
}

function msToTime (ms) {
    var seconds = parseInt(ms/1000);
    var minutes = parseInt(seconds/60, 10);
    seconds = seconds%60;
    var hours = parseInt(minutes/60, 10);
    minutes = minutes%60;

    return hours + ':' + minutes;
}
function parseTimeToLong(clockPicker, type) {
    // console.log(type);
    // console.log("log: " + $('.clockpickerFrom #ParkingFrom').val());
    var time = $('.' + clockPicker + ' #' + type).val();
    // console.log("Time: " + time);
    var temp = time.split(":")
    var hour = temp[0];
    // console.log("hour: " + hour);
    var minute = temp[1];
    // console.log("Minute: " + minute);
    // console.log("hour ms: " + parseInt(hour * 3600000));
    // console.log("minute ms: " + parseInt(minute * 60000));
    var ms = parseInt(hour * 3600000) + parseInt(minute * 60000);
    // console.log(ms);
    $('#allowed' + type).val(ms);
}
function convertDate(dateTypeLong) {
    if (dateTypeLong === null){
        return "Empty";
    }
    var dateStr = new Date(dateTypeLong),
        dformat = [dateStr.getDate(),
                dateStr.getMonth()+1,
                dateStr.getFullYear()].join('-')+' '+
            [dateStr.getHours(),
                dateStr.getMinutes(),
                dateStr.getSeconds()].join(':');
    return dformat;
}

function convertTime(dateTypeLong) {
    if (dateTypeLong === null){
        return "Empty";
    }
    var dateStr = new Date(dateTypeLong),
        dformat =
            [dateStr.getHours(),
                dateStr.getMinutes()].join(':');
    return dformat;
}

function convertDateToMs(dateStr) {
    if (dateStr === "") {
        return "";
    }
    var time = dateStr.split("-")
    var date = new Date(time[1] + "-" + time[0] + "-" + time[2])

    var ms = date.getTime();
    console.log(ms);
    // alert(ms);
    // console.log("MS: "+ms);

    // console.log("redundant: "+(parseInt(23 * 3600000) + parseInt(59 * 60000) + parseInt(59)));
    // console.log(convertDate(154030));
    return ms;

}
function sortHeaders() {
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
}