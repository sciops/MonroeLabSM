'use strict';

(function (angular) {
    'use strict';
    angular.module('ngRouteExample', ['ngRoute'])
            .controller('BoxKeyController', ['$scope', 'BoxKeyService', function ($scope, BoxKeyService) {
                    //radio button/checkbox hiding        
                    $scope.message = 'Please click Submit above.';
                    //$scope.params = $routeParams;
                    $scope.denomradio = 'yesdenom';
                    $scope.isShown = function (denomradio) {
                        return (denomradio === $scope.denomradio);
                    };
                    $scope.submit = function () {
                        $scope.message = 'SUBMITTED!';
                        //code here to actually submit the boxkey JSON somewhere
                    }
                    
                    var self = this;
                    self.key = {id: null, serial: '', opnum: '', datetime: ''};
                    self.keys = [];
                    self.fetchAllBoxKeys = function () {
                        BoxKeyService.fetchAllBoxKeys()
                                .then(
                                        function (d) {
                                            self.keys = d;
                                        },
                                        function (errResponse) {
                                            console.error('Error while fetching keys');
                                        }
                                );
                    };
                    self.createBoxKey = function (key) {
                        BoxKeyService.createBoxKey(key)
                                .then(
                                        self.fetchAllBoxKeys,
                                        function (errResponse) {
                                            console.error('Error while creating Key.');
                                        }
                                );
                    };
                    self.updateBoxKey = function (key, id) {
                        BoxKeyService.updateBoxKey(key, id)
                                .then(
                                        self.fetchAllBoxKeys,
                                        function (errResponse) {
                                            console.error('Error while updating Key.');
                                        }
                                );
                    };
                    self.deleteBoxKey = function (id) {
                        BoxKeyService.deleteBoxKey(id)
                                .then(
                                        self.fetchAllBoxKeys,
                                        function (errResponse) {
                                            console.error('Error while deleting Key.');
                                        }
                                );
                    };
                    self.fetchAllBoxKeys();
                    self.submit = function () {
                        if (self.key.id == null) {
                            console.log('Saving New Key', self.key);
                            self.createBoxKey(self.key);
                        } else {
                            self.updateBoxKey(self.key, self.key.id);
                            console.log('Key updated with id ', self.key.id);
                        }
                        self.reset();
                    };
                    self.edit = function (id) {
                        console.log('id to be edited', id);
                        for (var i = 0; i < self.keys.length; i++) {
                            if (self.keys[i].id == id) {
                                self.key = angular.copy(self.keys[i]);
                                break;
                            }
                        }
                    };
                    self.remove = function (id) {
                        console.log('id to be deleted', id);
                        for (var i = 0; i < self.keys.length; i++) {
                            if (self.keys[i].id == id) {
                                self.reset();
                                break;
                            }
                        }
                        self.deleteBoxKey(id);
                    };
                    self.reset = function () {
                        self.key = {id: null, keyname: '', address: '', email: ''};
                        $scope.myForm.$setPristine(); //reset Form
                    };
                }])
            .controller('MainController', function ($scope, $route, $routeParams, $location) {
                $scope.$route = $route;
                $scope.$location = $location;
                $scope.$routeParams = $routeParams;
            })
            .controller('homeController', function ($scope, $routeParams) {
                var vm = this;
                vm.message = 'Welcome to the home page!';
                $scope.name = "homeController";
                $scope.params = $routeParams;
            })
            .controller('saleController', function ($scope, $routeParams) {
                var vm = this;
                vm.message = 'This is the sale page!';
                $scope.name = "saleController";
                $scope.params = $routeParams;
            })
            .controller('servicesController', function ($scope, $routeParams) {
                $scope.message = 'Look! I am an about page.';
                $scope.name = "servicesController";
                $scope.params = $routeParams;
            })
            .controller('aboutController', function ($scope, $routeParams) {
                $scope.message = 'Look! I am an about page.';
                $scope.name = "aboutController";
                $scope.params = $routeParams;
            })
            .controller('contactController', function ($scope, $routeParams) {
                $scope.message = 'Look! I am an about page.';
                $scope.name = "contactController";
                $scope.params = $routeParams;
            })
            .controller('inputController', function ($scope, $routeParams) {
                $scope.message = 'Please click Submit above.';
                $scope.name = "inputController";
                $scope.params = $routeParams;
                $scope.denomradio = 'yesdenom';
                $scope.isShown = function (denomradio) {
                    return (denomradio === $scope.denomradio);
                };
                $scope.submit = function () {
                    $scope.message = 'SUBMITTED!';
                    //code here to actually submit the boxkey JSON somewhere
                }
            })
            .config(function ($routeProvider, $locationProvider) {
                $routeProvider
                        .when('/home', {
                            templateUrl: 'home.html',
                            controller: 'homeController',
                            controllerAs: 'home'
                        })
                        .when('/', {
                            templateUrl: 'pages/services.html',
                            controller: 'mainController'
                        })
                        .when('/sale', {
                            templateUrl: 'pages/sale.html',
                            controller: 'saleController',
                            controllerAs: 'sale'
                        })
                        .when('/services', {
                            templateUrl: 'pages/services.html',
                            controller: 'servicesController'
                        })
                        .when('/about', {
                            templateUrl: 'pages/about.html',
                            controller: 'aboutController'
                        })
                        .when('/contact', {
                            templateUrl: 'pages/contact.html',
                            controller: 'contactController'
                        })
                        .when('/input', {
                            templateUrl: 'pages/input.html',
                            controller: 'inputController'
                        });
                // configure html5 to get links working on jsfiddle
                $locationProvider.html5Mode({enabled: true});
            })
            .factory('BoxKeyService', ['$http', '$q', function ($http, $q) {
                    return {
                        fetchAllBoxKeys: function () {
                            return $http.get('http://localhost:8080/MonroeWeb/boxkey/')
                                    .then(
                                            function (response) {
                                                return response.data;
                                            },
                                            function (errResponse) {
                                                console.error('Error while fetching keys');
                                                return $q.reject(errResponse);
                                            }
                                    );
                        },
                        createBoxKey: function (key) {
                            return $http.post('http://localhost:8080/MonroeWeb/boxkey/', key)
                                    .then(
                                            function (response) {
                                                return response.data;
                                            },
                                            function (errResponse) {
                                                console.error('Error while creating key');
                                                return $q.reject(errResponse);
                                            }
                                    );
                        },
                        updateBoxKey: function (key, id) {
                            return $http.put('http://localhost:8080/MonroeWeb/boxkey/' + id, key)
                                    .then(
                                            function (response) {
                                                return response.data;
                                            },
                                            function (errResponse) {
                                                console.error('Error while updating key');
                                                return $q.reject(errResponse);
                                            }
                                    );
                        },
                        deleteBoxKey: function (id) {
                            return $http.delete('http://localhost:8080/MonroeWeb/boxkey/' + id)
                                    .then(
                                            function (response) {
                                                return response.data;
                                            },
                                            function (errResponse) {
                                                console.error('Error while deleting key');
                                                return $q.reject(errResponse);
                                            }
                                    );
                        }
                    };
                }]);
})(window.angular);


