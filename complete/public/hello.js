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
            $scope.userout = response.data;
        });

});
