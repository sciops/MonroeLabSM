var app = angular.module('app', ['ui.router'])
        //this configures the ui router to display new pages when the navbar is clicked
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
        //this controller manages the CRUD input form.
        .controller('BoxKeyController', ['$scope', 'BoxKeyService', function ($scope, BoxKeyService) {
                //storage of the key to be added or edited
                var self = this;
                self.key = {seed: null, digest: '', publickey: '', privatekey: ''};
                //storage of a list of keys to be viewed or edited
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
                self.updateBoxKey = function (key) {
                    BoxKeyService.updateBoxKey(key)
                            .then(
                                    self.fetchAllBoxKeys,
                                    function (errResponse) {
                                        console.error('Error while updating Key.');
                                    }
                            );
                };
                self.deleteBoxKey = function (publickey) {
                    BoxKeyService.deleteBoxKey(publickey)
                            .then(
                                    self.fetchAllBoxKeys,
                                    function (errResponse) {
                                        console.error('Error while deleting Key.');
                                    }
                            );
                };
                self.fetchAllBoxKeys();
                self.submit = function () {
                    //console.log("Submit function entered. publickey:" + self.key.publickey);
                    if (self.key.publickey.length === 0) { //http://stackoverflow.com/questions/154059/how-do-you-check-for-an-empty-string-in-javascript
                        //console.log('Saving New Key: ', self.key.seed);
                        self.createBoxKey(self.key);
                    } else {
                        //console.log('Updating Key: ', self.key.publickey);
                        self.updateBoxKey(self.key);
                    }
                    self.key = {id: null, keyname: '', address: '', email: ''};
                    $scope.myForm.$setPristine(); //reset Form
                };
                self.edit = function (publickey) {
                    //find the key by this publickey.
                    for (var i = 0; i < self.keys.length; i++) {
                        if (self.keys[i].publickey === publickey) {
                            self.key = angular.copy(self.keys[i]);
                            break;
                        }
                    }
                };
                self.remove = function (publickey) {
                    console.log('publickey to be deleted', publickey);
                    self.deleteBoxKey(publickey);
                };
                self.reset = function () {
                    self.key = {id: null, keyname: '', address: '', email: ''};
                    $scope.myForm.$setPristine(); //reset Form
                };
            }])
        //this factory determines what URLs to use for requests.
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
                    deleteBoxKey: function (publickey) {
                        var url = '/key/' + publickey;
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


