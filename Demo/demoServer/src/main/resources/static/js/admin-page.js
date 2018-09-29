/**
 * Created by asus on 3/29/2018.
 */
var ws = "http://localhost:8080/"
var JWT = window.localStorage.getItem('JWT');
$(document).ready(function () {
    loadSpots();
});

function loadSpots() {
    $.ajax({
       url: ws+"spot/loadAllSpots",
        type:'GET',
        headers: {
            'Authorization': JWT
        },
        success: function (res, status) {
           console.log(res);
           console.log(status);
           if (status !== "nocontent") {
               var tableContent;
               var i;
               var status ="Unknown";
               for (i = 0; i < res.length; i++) {
                   if (res[i].enable === true) {
                       status = "Enable";
                   } else {
                       status = "Disable";
                   }
                   tableContent +=
                       '<tr>\
                         <td>'+ res[i].spotName +'</td>\
                         <td>'+ res[i].address +'</td>\
                         <td>'+ res[i].favouriteCount +'</td>\
                         <td>'+ res[i].commentCount +'</td>\
                         <td>'+ status +'</td>\
                         <td><a href="#" class="waves-effect waves-light btn-small" onclick="changeStatus('+ res[i].id +')">Change Status</a></td>\
                         <td><a  href="#" class="waves-effect waves-light btn-small" onclick="updateSpot(' +res[i].id + ')">Edit</a></td>\
                    </tr>';
               }
               $('.highlight').find('tbody').append(tableContent);
           }
        }, error: function () {
            console.log("Failed to load data");
        }
    });
}

function updateSpot(id) {
    window.location = "updateSpot?spotId="+id;
}

function changeStatus(id) {
    $.ajax({
        url: ws+"spot/changeStatusSpot/"+id,
        type: 'GET',
        headers: {
            'Authorization': JWT
        },
        success: function (res) {
            location.reload(true);
        },
        error: function () {
            console.log("Failed to delete");
        }

    });
}