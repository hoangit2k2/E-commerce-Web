app.controller('statisticControl', ($scope) => {
    (async (...getTo) => {
        $scope.breadcrumbs.splice(2, 2, {name:'thống kê'});
        for (to of getTo) if (typeof (to) == 'string') await $scope.get(to);
        else {
            let keys = Object.keys(to)
            for (k of keys) await $scope.get(to[k], k);
        };
    })()

    $scope.statistic = async (procedure, label, colname, ...colData) => {
        let top = $scope.top || 1000;
        let by = $scope.by || 2; // 1 | 2 | 3
        $scope.heading = label;
        await $scope.get(`statistic?proc=${procedure}&o=${top}&o=${by}`);
        statistic.chart(showChart, $scope.data, label, colname, ...colData)
    }

});

class statistic {
    static chart(showInto, data, label, colSubtit, ...colData) {
        if(!colData || !colData.length) throw 'input column\'name is null!!!';
        if(!data || !showInto) throw 'data || showInto maybe null';
        
        var datasets = [];
        var options = {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }

        for (let col of colData) {
            let isStr = typeof col === 'string';
            var colors = [], borders = [];
            datasets.push({
                label: label || 'Thống kê dữ liệu',
                data: data.map(x => x[isStr ? col : col.n || 'quantity']),
                backgroundColor: colors,
                borderColor: borders,
                borderRadius: 0,
                borderWidth: 1,
                type: isStr ? 'bar' : col.t
            })
            statistic.#randomColor(data.length, colors, borders);
        }

        statistic.#chartSet(showInto,'bar', data.map(x => x[colSubtit||'name']), options, ...datasets);
    };

    static #chartSet(showChart, type, labels, options, ...datasets) {
        if(!showChart) return;
        while (showChart.firstChild) showChart.removeChild(showChart.lastChild);
        showChart.innerHTML = `<canvas class="bg-white bg-opacity-50 p-3 rounded-3 mb-3"></canvas>`

        new Chart(showChart.firstChild, {
            type: type,
            data: {
                labels: labels,
                datasets: datasets
            }, options: options
        });
    };

    static #randomColor(size, colors, borders) {
        if(colors, borders) for (let i = 0; i < size; i++) {
            let color = Math.random() * 360;
            colors.push(`hsl(${color}, 60%, 80%, .9)`);
            borders.push(`hsl(${color}, 80%, 60%, 1)`);
        }
    }
}