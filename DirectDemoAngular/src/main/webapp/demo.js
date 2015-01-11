/**
 * Expose gwt model to angular.
 */
dmWrapSignal = function(prop) {
			// turns signal into getterSetter
			return function(newVal) {
				if (angular.isDefined(newVal)) {
					prop.set(newVal);
				}
				return prop.get();
			}
		};
window.gwtStarted = function(zip) {
	angular.module('directDemo', [])

	.controller('directDemoController', [ '$scope', function($scope) {
		// $scope.demo = init; //window.demo;
		// $scope.calculator = calc; //window.calc;
		$scope.zip = zip;
		$scope.dmWrapSignal = window.dmWrapSignal;
		window.angularScope = $scope;

	} ])

	.directive('raboText', function() {
		return {
    		require: ['raboText', 'ngModel'],
			scope : {
				label : '@',
				ngModel : '='
			},
			templateUrl : 'rabo-text.html'
		}
	})

	// window.demo = model;
	// window.calc = calc;
	angular.bootstrap(document, [ 'directDemo' ]);
};
