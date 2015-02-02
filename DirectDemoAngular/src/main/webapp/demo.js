/**
 * Expose gwt model to angular.
 */
window.gwtStarted = function(adapter, model) {
	angular.module('directAngular', [])

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

	angular.bootstrap(document, [ 'directAngular' ]);
};
