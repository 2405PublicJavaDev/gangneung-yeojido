let selectedCategory = [];
let selectedRegion = [];
let canvas = document.getElementById("myCanvas"),
    ctx = canvas.getContext("2d"),
    canvasWidth = canvas.width,
    canvasHeight = canvas.height,
    bounds = { maxLon: 1, minLon: 200, maxLat: 1, minLat: 200 };

// 각 좌표들을 순회하며 최소 및 최대 경도와 위도를 계산합니다.
data.features.forEach(function (feature) {
    feature.geometry.coordinates.forEach(function (coordsSet) {
        coordsSet.forEach(function (coords) {
            coords.length <= 1
                ? updateBounds(coords)
                : coords.forEach(function (coord) {
                    updateBounds(coord);
                });
        });
    });
});

bounds.minLon -= 0.05;
bounds.minLat -= 0.05;
bounds.maxLon += 0.05;
bounds.maxLat += 0.05;

let drawnPolygons = [];

// 각 지리 데이터를 순회하며 캔버스에 폴리곤을 그립니다.
data.features.forEach(function (feature) {
    let polygonData = {
        name: feature.properties.adm_nm,
        properties: feature.properties,
        paths: [],
        lines: feature.lines,
    };
    feature.geometry.coordinates.forEach(function (coordsSet) {
        let currentPath = [];

        coordsSet.forEach(function (coords) {
            currentPath = [];
            coords.forEach(function (coord) {
                let x = lonToX(coord[0]),
                    y = latToY(coord[1]);
                currentPath.push({ x: x, y: y, lon: coord[0], lat: coord[1] });
            });
            polygonData.paths.push(currentPath);
        });
    });

    drawnPolygons.push(polygonData);
});

let sortedPolygons = [];

// 경도와 위도를 이용해 캔버스의 X 좌표를 계산합니다.
function lonToX(lon) {
    let normalizedLon = (lon - bounds.minLon) / (bounds.maxLon - bounds.minLon);
    return canvasWidth * normalizedLon;
}

// 경도와 위도를 이용해 캔버스의 Y 좌표를 계산합니다.
function latToY(lat) {
    let normalizedLat = (lat - bounds.minLat) / (bounds.maxLat - bounds.minLat);
    return canvasHeight - canvasHeight * normalizedLat;
}

// 캔버스의 X 좌표를 이용해 경도를 계산합니다.
function xToLon(x) {
    let normalizedX = x / canvasWidth;
    return normalizedX * (bounds.maxLon - bounds.minLon) + bounds.minLon;
}

// 캔버스의 Y 좌표를 이용해 위도를 계산합니다.
function yToLat(y) {
    let normalizedY = 1 - y / canvasHeight;
    return normalizedY * (bounds.maxLat - bounds.minLat) + bounds.minLat;
}

// 경도와 위도를 바탕으로 최소, 최대 값을 업데이트합니다.
function updateBounds(coords) {
    let lon = coords[0],
        lat = coords[1];
    bounds.minLon = Math.min(lon, bounds.minLon);
    bounds.maxLon = Math.max(lon, bounds.maxLon);
    bounds.minLat = Math.min(lat, bounds.minLat);
    bounds.maxLat = Math.max(lat, bounds.maxLat);
}

// 포인트가 특정 폴리곤 내부에 있는지 확인합니다.
// Ray casting algorithm 사용함
function getPolygon(transformedPoint) {
    let foundPolygon;
    sortedPolygons.forEach(function (polygon) {
        let crossings = 0;
        for (let i = 0; i < polygon.path.length; i++) {
            let currPoint = polygon.path[i],
                nextPoint = polygon.path[(i + 1) % polygon.path.length];
            if (
                currPoint.y > transformedPoint.y !==
                nextPoint.y > transformedPoint.y
            ) {
                let intersectX =
                    ((nextPoint.x - currPoint.x) * (transformedPoint.y - currPoint.y)) /
                    (nextPoint.y - currPoint.y) +
                    currPoint.x;
                if (transformedPoint.x < intersectX) crossings++;
            }
        }
        if (crossings % 2 > 0) foundPolygon = polygon;
    });
    return foundPolygon;
}

// 폴리곤을 y 좌표에 따라 내림차순으로 정렬합니다.
drawnPolygons.forEach(function (polygon) {
    let sortedPolygon = { name: polygon.name, path: [] };
    polygon.paths.forEach(function (path) {
        path.forEach(function (point) {
            sortedPolygon.path.push({ x: point.x, y: point.y });
        });
    });
    sortedPolygon.path.sort(function (a, b) {
        return b.y - a.y;
    });
    sortedPolygons.push(sortedPolygon);
});

// 그려진 폴리곤들 + 마커를 캔버스에 그립니다.
function drawGangneung() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    drawGangneungFill();
    drawGangneungStroke();
}

function drawGangneungFill() {
    drawnPolygons.forEach(function (polygon) {
        polygon.paths.forEach(function (path) {
            ctx.save();
            ctx.beginPath();
            let isFirstPoint = true;
            path.forEach(function (point) {
                if (isFirstPoint) {
                    ctx.moveTo(point.x, point.y);
                    isFirstPoint = false;
                } else {
                    ctx.lineTo(point.x, point.y);
                }
            });
            ctx.strokeStyle = "black";
            // ctx.stroke();
            if(selectedRegion.includes(polygon.name)) {
                ctx.fillStyle = selectedColorMap.get(polygon.name);
            } else {
                ctx.fillStyle = colorMap.get(polygon.name);
            }
            ctx.fill();
            ctx.closePath();
            ctx.restore();
        });
    });
}

function drawGangneungStroke() {
    drawnPolygons.forEach(function (polygon) {
        for (let pathIdx = 0; pathIdx < polygon.paths.length; pathIdx++) {
            const path = polygon.paths[pathIdx];
            if (polygon.lines) {
                polygon.lines[pathIdx].forEach((line) => {
                    let fromIdx = -1;
                    let toIdx = -1;
                    path.forEach((point, idx) => {
                        if (
                            point.lon === line.fromLon &&
                            point.lat === line.fromLat &&
                            fromIdx === -1
                        ) {
                            fromIdx = idx;
                        }
                        if (
                            point.lon === line.toLon &&
                            point.lat === line.toLat &&
                            toIdx === -1
                        ) {
                            toIdx = idx;
                        }
                    });
                    if(line.isLast) {
                        toIdx = path.length - 1;
                    }
                    console.log(fromIdx, toIdx);
                    ctx.save();
                    ctx.beginPath();
                    let isFirstPoint = true;
                    path.forEach((point, idx) => {
                        if (fromIdx <= idx && idx <= toIdx) {
                            if (isFirstPoint) {
                                ctx.moveTo(point.x, point.y);
                                isFirstPoint = false;
                            } else {
                                ctx.lineTo(point.x, point.y);
                            }
                        }
                    });
                    if (line.style === "OUTER") {
                        ctx.strokeStyle = "black";
                    } else {
                        ctx.strokeStyle = "red";
                        ctx.setLineDash([5,3]);/*dashes are 5px and spaces are 3px*/
                    }
                    ctx.stroke();
                    ctx.closePath();
                    ctx.restore();
                });
            }
        }
    });
}
drawGangneung();
let curPolygon
canvas.addEventListener("mousemove", function(e) {
    e.preventDefault();
    const rect = canvas.getBoundingClientRect();
    const point = {
        x: e.clientX - rect.left,
        y: e.clientY - rect.top,
    };
    // 스케일과 오프셋을 고려한 마우스 좌표 변환
    let polygon = getPolygon(point);
    if (polygon) {
        curPolygon = polygon;
    }
});

canvas.addEventListener("mousedown", function(e) {
    e.preventDefault();
    if(curPolygon) {
        const index = selectedRegion.indexOf(curPolygon.name);
        if (index !== -1) {
            selectedRegion.splice(index, 1);
        } else {
            selectedRegion.push(curPolygon.name);
        }
        drawGangneung();
        applyFilter();
    }
});

