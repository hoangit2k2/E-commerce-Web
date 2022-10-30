const app = angular.module('app', ['ngRoute']);
const serverIO = 'http://localhost:8080';
const pathIO = 'rest/statistic';

app.config(function ($routeProvider) {
    $routeProvider.when("/home", {
        templateUrl : "./home.htm"
    }).when("/statisticAS", {
        templateUrl : "./statisticAS.htm", controller: 'control_AS'
    }).when("/statisticCS", {
        templateUrl : "./statisticCS.htm", controller: 'control_CS'
    }).when("/statisticLS", {
        templateUrl : "./statisticLS.htm", controller: 'control_LS'
    }).otherwise({
        redirectTo: "/home"
    });
});

app.controller('control', function ($http, $scope) {

    
    console.log('A');
});

// THỐNG KÊ NỘI DUNG TẢI LÊN THEO TÀI KHOẢN
app.controller('control_AS', function ($http, $scope) {

    // onload filter
    $http.get(getLink(serverIO,pathIO,'as?t=MIN_MAX')).then(r => {
        $scope.fil = r.data[0];
        $scope.fil.end = new Date($scope.fil.et);
        $scope.fil.start = new Date($scope.fil.st);
        $scope.chart_AS($scope.fil);
    }).catch(e => console.error(e))

    // show data with new chartJS
    $scope.chart_AS = function(fil) {
        if(!fil) fil=$scope.fil;

        let qty = fil.qty ? fil.qty : 15; // số lượng
        let st = moment(fil.start).format('Y-MM-DD hh:mm:ss'); // start date
        let et = moment(fil.end).format('Y-MM-DD hh:mm:ss'); // end date
        let desc = fil.desc; // order by quantity
        let path = `as?t=STATISTIC&qty=${qty}&st=${st}&et=${et+(desc!=undefined?`&desc=${desc}`:'')}`;
        
        $http.get(getLink(serverIO,pathIO,path)
        ).then(r => {
            if(!r.data) return;

            var colors = [];
            var borders = [];
            var options = {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            };
            var datasets = [
                {
                    label: 'Nội dung đã tải',
                    data: r.data.map(x => x['quantity']),
                    backgroundColor: colors,
                    borderColor: borders,
                    borderRadius: 3,
                    borderWidth: 1
                }
            ];

            for (let i = 0; i < r.data.length; i++) {
                let color = Math.random() * 360;
                colors.push(`hsl(${color}, 80%, 50%, .25)`);
                borders.push(`hsl(${color}, 80%, 50%, 1)`);
            }
            
            chartSet('bar', r.data.map(e=>e.name), options, ...datasets)
        }).catch(e => console.error(e))
    }

});

// THỐNG KÊ NỘI DUNG TẢI LÊN THEO THỜI GIAN
app.controller('control_CS', function ($http, $scope) {

    // onload filter
    $http.get(getLink(serverIO,pathIO,'cs?t=MIN_MAX')).then(r => {
        $scope.fil = r.data[0];
        $scope.fil.end = new Date($scope.fil.et);
        $scope.fil.start = new Date($scope.fil.st);
        $scope.chart_CS($scope.fil);
    }).catch(e => console.error(e))

    // show data with new chartJS
    $scope.chart_CS = function(fil) {
        if(!fil) fil=$scope.fil;

        let st = moment(fil.start).format('Y-MM-DD hh:mm:ss'); // start date
        let et = moment(fil.end).format('Y-MM-DD hh:mm:ss'); // end date
        let path = `cs?t=STATISTIC&a=${fil.a ? fil.a : 1}&st=${st}&et=${et}`;
        
        $http.get(getLink(serverIO,pathIO,path)
        ).then(r => {
            if (!r.data) return;
            // prepare data to config
            var colors = [];
            var borders = [];
            var options = {
                responsive: true,
                plugins: {
                    title: {
                        display: true,
                        text: 'Thống kê và dự kiến dữ liệu'
                    },
                },
                interaction: {
                    intersect: false,
                },
                scales: {
                    x: {
                        display: true,
                        title: {
                            display: true
                        }
                    },
                    y: {
                        display: true,
                        title: {
                            display: true,
                            text: 'Value'
                        },
                        suggestedMin: -1,
                        suggestedMax: Math.max(...r.data) + 1
                    }
                }
            };
            var datasets = [
                {
                    label: 'Nội dung đã tải',
                    data: r.data.map(x => x['quantity']),
                    backgroundColor: colors,
                    borderColor: borders,
                    borderRadius: 3,
                    pointRadius: 5,
                    pointHoverRadius: 10,
                    cubicInterpolationMode: 'monotone',
                    borderWidth: 1
                }, {
                    label: 'Dự kiến',
                    data: r.data.map(x => x['quantity']+.5),
                    backgroundColor: colors,
                    borderColor: '#0079ff39',
                    cubicInterpolationMode: 'monotone',
                    borderWidth: 3,
                    type: 'line'
                }
            ];
            for (let i = 0; i < r.data.length; i++) {
                let color = Math.random() * 360;
                colors.push(`hsl(${color}, 80%, 50%, .25)`);
                borders.push(`hsl(${color}, 80%, 50%, 1)`);
            }
            
            // show canvas
            chartSet('bar', r.data.map(e => e.about), options, ...datasets)
        }).catch(e => console.error(e))
    }

});

// THỐNG KÊ LƯỢT THÍCH
app.controller('control_LS', function ($http, $scope) {

    // onload filter
    $http.get(getLink(serverIO,pathIO,'ls?t=MIN_MAX')).then(r => {
        $scope.fil = r.data[0];
        $scope.fil.qty = 15;
        $scope.fil.end = new Date($scope.fil.et);
        $scope.fil.start = new Date($scope.fil.st);
        $scope.chart_LS($scope.fil);
    }).catch(e => console.error(e))

    // show data with new chartJS
    $scope.chart_LS = function(fil) {
        if(!fil) fil=$scope.fil;

        let qty = fil.qty ? fil.qty : fil.length; // số lượng
        let st = moment(fil.start).format('Y-MM-DD hh:mm:ss'); // start date
        let et = moment(fil.end).format('Y-MM-DD hh:mm:ss'); // end date
        let path = `ls?t=STATISTIC&&qty=${qty}&st=${st}&et=${et}`;
        
        $http.get(getLink(serverIO,pathIO,path)
        ).then(r => {
            if (!r.data) return;

            var colors = [];
            var borders = [];
            var options = {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            };
            var datasets = [
                {
                    label: 'Tài khoản thích nội dung',
                    data: r.data.map(x => x['quantity']),
                    backgroundColor: colors,
                    borderColor: borders,
                    borderRadius: 3,
                    borderWidth: 1
                }
            ];
            for (let i = 0; i < r.data.length; i++) {
                let color = Math.random() * 360;
                colors.push(`hsl(${color}, 80%, 50%, .25)`);
                borders.push(`hsl(${color}, 80%, 50%, 1)`);
            }
            
            chartSet('bar', r.data.map(e => e.name), options, ...datasets)
        }).catch(e => console.error(e))
    }

});

function chartSet(type, labels, options, ...datasets) {
    const chart = document.querySelector('#showChart');
    if(!chart) return;
    while (chart.firstChild) chart.removeChild(chart.lastChild);
    chart.innerHTML = `<canvas class="border border-2"></canvas>`

    new Chart(chart.firstChild, {
        type: type,
        data: {
            labels: labels,
            datasets: datasets
        }, options: options
    });
}

