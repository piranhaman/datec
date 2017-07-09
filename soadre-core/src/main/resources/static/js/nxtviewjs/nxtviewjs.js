app.controller('HomeController', function ($rootScope, $scope, $location, $rootScope, $http, $state) {
    $scope.$state = $state;
    
    // Se calcula el context path
    var absolutePath = $location.$$absUrl;
    var slashes = 0;
    var index;
    for (var i = 0; i < absolutePath.length; i++) {
        if (absolutePath[i] === "/") {
            slashes++;
            index = i;
        }
        if (slashes === 4) {
            break;
        }
    }
    $rootScope.contextPath = absolutePath.substring(0, index);
    $http.get($rootScope.contextPath + '/api/usuario').then(function (response) {
        $rootScope.usuario = response.data;
    });
    
    $rootScope.Utils = {
        keys: Object.keys
    };
    
    $rootScope.convertirAFecha = function (mes, ano) {
        return new Date(ano, mes-1);
    };
   
});

app.factory('nxtviewInterceptor', function ($q, $window, $rootScope, $injector) {
    return {
        'response': function(response) {
                //Ya que angular no soporta el redirect a login cuando la sesión expira, con esto se replica este funcionamiento
                if (!response.config.url.endsWith('login') && typeof response.data === 'string' && response.data.indexOf("##redirigir a login##")>-1) {  
                    $window.location.href = $rootScope.contextPath + "/login";
                    return $q.reject(response);
                } else {                    
                    return response;                  
                }                
            },
        'responseError': function(rejection) {                
                var uibModalInstance = $injector.get('$uibModal').open({
                    templateUrl: 'js/nxtviewjs/mensaje-excepcion.html',
                    controller: ['$scope', 'mensaje', function ($scope, mensaje) {                        
                        $scope.mensaje = mensaje,
                        $scope.ok = function () {
                            uibModalInstance.close();
                        };
                    }],
                    size: 200,
                    resolve: {
                        mensaje: function () {
                            switch (rejection.status) {
                                case 404:
                                    return 'Error de conexión';
                                    break;
                                default:
                                    if (rejection.data && rejection.data.message) {
                                        return rejection.data.message.split('\n');
                                    } else if (rejection.message) {
                                        return ["Error interno. " + rejection.message];
                                    } else {
                                        return ["Error desconocido"];
                                    }
                            }
                        }
                    }
                }); 
                return $q.reject(rejection);
        }
  };
});

app.config(function ($httpProvider) {
  $httpProvider.interceptors.push('nxtviewInterceptor');
});

app.controller('LogoutController', function ($uibModal, $http, $location, $rootScope, manejoExcepciones) {
    $http.get($rootScope.contextPath + '/api/salir').then(function () {
        $location.path('/login.html');
    });

});
app.factory('manejoExcepciones', function ($rootScope, $location) {
    var manejoExcepciones = {};
    manejoExcepciones.tratarExcepcion = function ($uibModal, data) {
        $uibModal.open({
            templateUrl: 'js/nxtviewjs/mensaje-excepcion.html',
            controller: 'manejoExcepcionesController',
            size: 200,
            resolve: {
                mensaje: function () {
                    switch (data.status) {
                        case 404:
                            return 'Error de conexión';
                        case 102:
                            $location.url($rootScope.contextPath + '/login.html');
                            break;
                        default:
                            if (data.data && data.data.message) {
                                return data.data.message;
                            } else if (data.message) {
                                return "Error interno. " + data.message;
                            } else {
                                return "Error desconocido";
                            }
                    }
                }
            }
        });
    };
    return manejoExcepciones;
});

app.factory('mensajeEspera', function ($rootScope) {
    var mensajeEspera = {};
    var uibModalInstance;
    mensajeEspera.abrir = function ($uibModal) {
        uibModalInstance = $uibModal.open({
            templateUrl: 'mensaje-espera.html',
            /*controller: 'mensajeEsperaController',*/
            size: 200,
            /*resolve: {
             mensaje: function () {
             switch (data.status) {
             case 404:
             return 'Error de conexión';
             break;
             default:
             return data.data.message;
             }
             }
             }*/
            backdrop: 'static', /*  this prevent user interaction with the background  */
            keyboard: false
        });
    };
    mensajeEspera.cerrar = function () {
        uibModalInstance.dismiss('cancel');
    }
    return mensajeEspera;
});



app.factory('mensajeConfirmacion', function () {
    var mensajeConfirmacion = {};
    mensajeConfirmacion.abrir = function ($uibModal, titulo, mensaje, postConfirmacion) {
        $uibModal.open({
            templateUrl: 'js/nxtviewjs/mensaje-confirmacion.html',
            controller: function ($scope, $uibModalInstance) {
                $scope.titulo = titulo;
                $scope.mensaje = mensaje;
                $scope.ok = function () {
                    $uibModalInstance.close();
                },
                        $scope.cancel = function () {
                            $uibModalInstance.dismiss('cancel');
                        };
            },
        }).result.then(function () {
            postConfirmacion();
        });

    };
    return mensajeConfirmacion;
});

app.factory('mensajeInformacion', function ($rootScope) {
    var mensajeInformacion = {};
    mensajeInformacion.abrir = function ($uibModal, mensaje) {
        $uibModal.open({
            templateUrl: 'js/nxtviewjs/mensaje-informacion.html',
            controller: function ($scope, $uibModalInstance) {
                $scope.mensaje = mensaje;
                $scope.ok = function () {
                    $uibModalInstance.close();
                };
            },
        });
    };
    return mensajeInformacion;
});

app.controller('manejoExcepcionesController', function ($scope, $uibModalInstance, mensaje) {
    $scope.mensaje = mensaje;
    $scope.ok = function () {
        $uibModalInstance.close();
    };
});