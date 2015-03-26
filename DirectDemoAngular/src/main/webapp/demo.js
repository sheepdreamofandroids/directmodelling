/**
 * Expose gwt model to angular.
 */
window.gwtStarted = function(adapter, model) {
	angular.module('directAngular', ['ngMaterial'])

	.controller('DirectAngularController', [ '$scope', function($scope) {
		adapter($scope);
		$scope.model = model;
		window.angularScope = $scope;
	} ])

	.directive('dmField', function() {
		return {
	        transclude: true,
			scope : {
				label : '@',
				model : '&'
			},
			controller : function($scope) {
				adapter($scope);
			},
			templateUrl : 'dm-field.html',
		}
	})

	.directive('dmNumber', function() {
		return {
			require : [],
			controller : function($scope) {
				adapter($scope);
			},
			scope : {
				label : '@',
				model : '&'
			},
			templateUrl : 'dm-number.html',
		}
	})

	.directive('dmText', function() {
		return {
			require : [],
			controller : function($scope) {
				adapter($scope);
			},
			scope : {
				label : '@',
				model : '&'
			},
			templateUrl : 'dm-text.html',
		}
	})

	.controller('AppCtrl', ['$scope', '$mdSidenav', function($scope, $mdSidenav){
		$scope.toggleSidenav = function(menuId) {
    		$mdSidenav(menuId).toggle();
  		};
  		$scope.tab=0;
  		$scope.choose = function(section) {
  			$scope.tab = section;
  		}
 	}]);
 	
	angular.bootstrap(document, [ 'directAngular' ]);
};
var lskdflkjsdflkjsdf="lskdjflksjdflkjsdlfkj";
