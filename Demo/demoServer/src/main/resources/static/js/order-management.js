$(document).ready(function () {
    initOrders();
    sortHeaders();

    $('#searchBtn').on('click', function (e) {
        e.preventDefault();
        filterOrder(0);
    });
});
function initOrders() {
    $.ajax({
        type: "GET",
        dataType: "json",
        url: 'http://localhost:8080/order/get-orders',
        success: function (data) {
            console.log(data);
            loadData(data);
        }, error: function () {
            alert("Can't load data")
        }
    });
}
function loadData(res) {
    var content = "";
    content = res.data;
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
        row += '<td>' + content[i].userId.lastName + '</td>';
        row += '<td>' + content[i].userId.phoneNumber + '</td>';
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
        // var orderObject = JSON.stringify(content[i]);
        // row += '<td><a href="#" onclick="viewPricingDetail(' + orderObject + ')" class="btn btn-primary viewBtn">View Detail</a></td>';
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
                '<a href="#" class="nav-link" onclick="filterOrder(' + currentPage + ')">' + currentPage + '</a>\n' +
                '</li>';
            $('#pagination').append(li);
        } else {

            li = '<li class="nav-item">\n' +
                '<a href="#" class="nav-link" onclick="filterOrder(' + currentPage + ')">\n' +
                +currentPage + '</a>\n' +
                '</li>';
            $('#pagination').append(li);
        }
    }
}

function filterOrder(pageNumber) {
    var url = "http://localhost:8080/order/filter-order";
    if (pageNumber != null) {
        url = url+"?page="+pageNumber;
    }
    var searchType = $('#search-filter option:selected').val();
    var searchValue =  $('#searchValue').val();
    console.log("Search By: "+searchType);
    console.log("SearchValue: "+searchValue);

    var filterObject = createSearchObject(searchType, ":", searchValue);
    $.ajax({
        type:'POST',
        url: url,
        dataType:"json",
        contentType: 'application/json',
        data: JSON.stringify(filterObject),
        success:function(response){
            emptyTable();
            emptyPaginationLi();
            loadData(response);
            console.log(response);
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
            $('.myForm #lastName').text(order.userId.lastName);
            $('.myForm #phoneNumber').text(order.userId.phoneNumber);
            $('.myForm #location').text(order.locationId.location);
            $('.myForm #allowedParkingFrom').text(order.allowedParkingFrom);
            $('.myForm #allowedParkingTo').text(order.allowedParkingTo);
            $('.myForm #checkInDate').text(convertDate(order.checkInDate));
            $('.myForm #checkOutDate').text(convertDate(order.checkOutDate));
            var row = "";
            emptyPricingTable();
            console.log("Pricing SIze: "+res.length);
            for ( i = 0; i < res.length; i++) {
                row = '<tr>';
                row += '<td>' + res[i].fromHour + '</td>';
                row += '<td>' + res[i].pricePerHour + '</td>';
                row += '<td>' + res[i].lateFeePerHour + '</td>';
                row += '</tr>';
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
            $('.myForm #total').text(total);
            $('.myForm #vehicleTypeId').text(order.userId.vehicleTypeId.name);
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

    return hours + 'h' + minutes + 'p' + seconds;
}

function convertDate(dateTypeLong) {
    if (dateTypeLong === null){
        return "Empty";
    }
    var dateStr = new Date(dateTypeLong),
        dformat = [dateStr.getMonth()+1,
                dateStr.getDate(),
                dateStr.getFullYear()].join('-')+' '+
            [dateStr.getHours(),
                dateStr.getMinutes(),
                dateStr.getSeconds()].join(':');
    return dformat;
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