$(document).ready(function () {
    initPolicies();

});
var listPolicyJson = [];
function initPolicies() {
    var policies = [];
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/policy/policies",
        dataType: "json",
        success: function (data) {
            if (data != null) {
                for (let i = 0; i < data.length; i++) {
                    policies.push(data);
                    getVehicleByPolicyId(data.id);
                }
                listPolicyJson.push(policies);
            }

        }, error: function (data) {
           console.log(data);
        }
    })
}

function getVehicleByPolicyId(policyId) {
    var vehicleAllowed = [];
    $.ajax({
        type: "GET",
        dataType: "json",
        url: "http://localhost:8080/policy-vehicleType/get-by-policy?policyId="+policyId,
        success: function (data) {
            if (data != null) {
                for (let i = 0; i < data.length; i++) {
                    vehicleAllowed.push(data);
                }
                listPolicyJson.push(vehicleAllowed);
            }
        }, error: function (data) {
            
        }
    })
}