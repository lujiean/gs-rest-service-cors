angular.module('demo', [])
.controller('Domain', function($scope) {
    $scope.domain = window.location.host;
})
.controller('Hello', function($scope, $http) {
    $http.get('http://localhost:8080/greeting').
        then(function(response) {
            // $scope.greeting = response.data;
            $scope.grt = response.data;
        });
})
.controller('User', function($scope, $http){

    //define
    $scope.clear = function (){
        $scope.userid = '';
        $scope.username = '';
        $scope.useremail = '';
    };
    $scope.search = function () {
        // if (typeof($scope.userid) != "undefined" && $scope.userid) {
        if (!isUndefinedOrNull($scope.userid)) {
            // url = 'http://localhost:8080/demo/findByID?id=' + $scope.userid;
            url = 'http://' + window.location.host +'/demo/findByID?id=' + $scope.userid;
            $scope.stype = "findByID";
        // } else if (typeof($scope.username) != "undefined" && $scope.username && typeof($scope.useremail) != "undefined" && $scope.useremail){
        } else if (!isUndefinedOrNull($scope.username) && !isUndefinedOrNull($scope.useremail)){
            // url = 'http://localhost:8080/demo/findBy2Param?name=' + $scope.username + '&name2=' + $scope.useremail;
            url = 'http://' + window.location.host + '/demo/findBy2Param?name=' + $scope.username + '&name2=' + $scope.useremail;
            $scope.stype = "findBy2Param";
        // } else if (typeof($scope.username) != "undefined" && $scope.username){
        } else if (!isUndefinedOrNull($scope.username)){
            // url = 'http://localhost:8080/demo/findByName?name=' + $scope.username;
            url = 'http://' + window.location.host + '/demo/findByName?name=' + $scope.username;
            $scope.stype = "findByName";
        } else {
            // url = 'http://localhost:8080/demo/all';
            url = 'http://' + window.location.host + '/demo/all';
            $scope.stype = "all";
        }
        // url = 'http://localhost:8080/demo/all';
        $http.get(url).
            then(function(response){
                $scope.users = response.data;
            });
    };
    $scope.adduser = function () {
        // link = 'http://localhost:8080/demo/add?name=' + fi_name + '&email=' + $scope.email
        // console.log(user);
        // link = 'http://localhost:8080/demo/add?name=' + $scope.username + '&email=' + $scope.useremail;
        link = 'http://' + window.location.host + '/demo/add?name=' + $scope.username + '&email=' + $scope.useremail;
        $http.get(link).
            then(function(response) {
                // $scope.greeting = response.data;
                $scope.rsp = response.data;
            });
        $scope.clear();
        $scope.search();
    };
    $scope.deluser = function () {
        // link = 'http://localhost:8080/demo/add?name=' + fi_name + '&email=' + $scope.email
        // console.log(user);
        link = 'http://' + window.location.host + '/demo/delete?id=' + $scope.userid;
        $http.get(link).
            then(function(response) {
                // $scope.greeting = response.data;
                $scope.rsp = response.data;
            });
        $scope.clear();
        $scope.search();
    };
    $scope.upduser = function () {
        // link = 'http://localhost:8080/demo/update?id=' + $scope.userid + '&name=' + $scope.username + '&email=' + $scope.useremail;
        link = 'http://' + window.location.host + '/demo/update?id=' + $scope.userid + '&name=' + $scope.username + '&email=' + $scope.useremail;
        $http.get(link).
            then(function(response){
                $scope.rsp = response.data;
            });
        $scope.clear();
        $scope.search();
    };
    
    //run search
    $scope.search();
})
;

function isUndefinedOrNull(a){
    if (!a || typeof(a) == 'undefined' ){
        return true;
    } else {
        return false;
    }
};