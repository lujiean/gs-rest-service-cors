angular.module('demo', [])
.controller('Hello', function($scope, $http) {
    $http.get('http://localhost:8080/greeting').
        then(function(response) {
            // $scope.greeting = response.data;
            $scope.grt = response.data;
        });
})
.controller('User', function($scope, $http){
    $http.get('http://localhost:8080/demo/all').
        then(function(response){
            $scope.users = response.data;
        });

})
.controller('UserAdd', function($scope, $http){
    $scope.adduser = function () {
        link = 'http://localhost:8080/demo/add?name=' + $scope.name + '&email=' + $scope.email
        $http.get(link).
            then(function(response) {
                // $scope.greeting = response.data;
                $scope.description = response.data;
            });
    };
})
;
