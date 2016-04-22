var app = angular.module('app', ['ui.router']);
//this configures the ui router to display new pages when the navbar is clicked
app.config(function ($stateProvider, $urlRouterProvider) {
    //.otherwise determines the default view
    $urlRouterProvider.otherwise("/services");
    $stateProvider
            //https://github.com/craigmckeachie/ui-router-examples/blob/master/ui-router-named-nested/js/main.js
            .state('app', {
                url: '/',
                views: {
                    'content': {
                        templateUrl: 'pages/services.html'
                    }
                }
            })
            .state('app.services', {
                url: 'services',
                views: {
                    'content@': {
                        templateUrl: 'pages/services.html'
                    }
                }
            })
            .state('app.contact', {
                url: 'contact',
                views: {
                    'content@': {
                        templateUrl: 'pages/contact.html'
                    }
                }
            })
            .state('app.about', {
                url: 'about',
                views: {
                    'content@': {
                        templateUrl: 'pages/about.html'
                    }
                }
            })
            .state('app.input', {
                url: 'input',
                views: {
                    'content@': {
                        templateUrl: 'pages/input.html'
                    }
                }
            })
            .state('app.projection', {
                url: 'projection',
                views: {
                    'content@': {
                        templateUrl: 'pages/projection.html',
                        controller: 'ProjectionController'
                    }
                }
            })
            .state('app.projection.serial', {
                url: '/projection/:projectionrequest',
                views: {
                    'detail@app.projection': {
                        templateUrl: 'pages/projection-detail.html',
                        controller: 'ProjectionDetailController'
                    }
                }
            })


            //old states
            /*
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
             */
            ;
});

//this controller manages the CRUD input form.
app.controller('BoxKeyController', ['$scope', 'BoxKeyService', function ($scope, BoxKeyService) {
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
    }]);

//this factory determines what URLs to use for requests in the CRUD form
app.factory('BoxKeyService', ['$http', '$q', function ($http, $q) {
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

app.controller('ProjectionController', function ($scope) {
    var self = this;

    self.request = {
        serials: [''],
        operator: '',
        heading: '',
        gpsx: '',
        gpsy: '',
        crypto: '',
        fiat: '',
        denominations: [''],
        start: '',
        end: '',
        frequency: ''
    };

    self.addNewSerial = function () {
        self.request.serials.push('');
    };
    self.removeSerial = function (index) {
        //http://stackoverflow.com/questions/5767325/remove-a-particular-element-from-an-array-in-javascript
        self.request.serials.splice(index, 1);
    };
    self.addNewDenomination = function () {
        self.request.denominations.push('');
    };
    self.removeDenomination = function (index) {
        self.request.denominations.splice(index, 1);
    };

});
app.controller('ProjectionDetailController', function ($scope) {
    $scope.selected = ProjectionService.find($stateParams.publickey);
});
app.factory('ProjectionService', function () {
    //this is a list of default values to debug with.
    var keys = [
        {
            serial: 1,
            name: 'Craig McKeachie',
            email: 'craig@test.com',
            description: 'Lorem ipsum dolor sit amet.'
        },
        {
            serial: 2,
            name: 'John Doe',
            email: 'johndoe@gmail.com',
            description: 'Lorem ipsum dolor sit amet.'
        }
    ];

    return {
        list: function () {
            return keys;
        },
        find: function (id) {
            return _.find(keys, function (key) {
                return key.publickey == publickey;
            })
        }
    }
})


