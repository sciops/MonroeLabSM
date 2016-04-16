var app = angular.module('app', ['ui.router'])
        .config(function ($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise("/services");
                $stateProvider
                .state('services', {
                url: '/services',
                        templateUrl: 'pages/services.html'
                })
                .state('contact', {
                url: '/contact',
                        templateUrl: 'pages/contact.html'
                })
                .state('about', {
                url: '/about',
                        templateUrl: 'pages/about.html'
                })
                .state('input', {
                url: '/input',
                        templateUrl: 'pages/input.html'
                })
                .state('projection', {
                url: '/projection',
                        templateUrl: 'pages/projection.html'
                })
                .state('state1.list', {
                url: '/list',
                        templateUrl: 'templates/state1.list.html',
                        controller: function ($scope) {
                        $scope.items = ["a", "list", "of", "items"];
                        }
                })
                .state('state2', {
                url: "/state2",
                        templateUrl: "templates/state2.html"
                })
                .state('state2.list', {
                url: "/list",
                        templateUrl: "templates/state2.list.html",
                        controller: function ($scope) {
                        $scope.things = ["A", "Set", "Of", "Things"];
                        }
                });
        })
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
                };
                var self = this;
                self.key = {seed: null, digest: ''};
                self.keys = [];
                self.fetchAllBoxKeys = function () {
                BoxKeyService.fetchAllBoxKeys()
                        .then(
                                function (d) {
                                self.keys = d;
                                        console.info('KEYS SET');
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
                self.updateBoxKey = function (key) {
                BoxKeyService.updateBoxKey(key)
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
                if (self.key.id === null) {
                console.log('Saving New Key', self.key);
                        self.createBoxKey(self.key);
                } else {
                self.updateBoxKey(self.key);
                        console.log('Key updated with id: ', self.key.id);
                }
                self.reset();
                };
                self.edit = function (id) {
                console.log('id to be edited', id);
                        for (var i = 0; i < self.keys.length; i++) {
                if (self.keys[i].id === id) {
                self.key = angular.copy(self.keys[i]);
                        break;
                }
                }
                };
                self.remove = function (id) {
                console.log('id to be deleted', id);
                        for (var i = 0; i < self.keys.length; i++) {
                if (self.keys[i].id === id) {
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
                };
        })
        .factory('BoxKeyService', ['$http', '$q', function ($http, $q) {
        return {
        fetchAllBoxKeys: function () {
        var url = '/keys';
                return $http.get(url)
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
                var url = '/key/' + JSON.stringify(key);
                        return $http.post(url)
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
                updateBoxKey: function (key) {
                var url = '/key/' + JSON.stringify(key);
                        return $http.put(url)
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
                var url = '/key/' + id;
                        return $http.delete(url)
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


