let selectedCategory = [];
let scale = 1; // 기본 스케일
let offsetX = 0; // X축 오프셋
let offsetY = 0; // Y축 오프셋
let isDragging = false; // 드래그 여부
let startX, startY; // 드래그 시작 위치
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

// 마우스 움직임에 따라 폴리곤 이름을 출력합니다.
canvas.addEventListener("mousemove", function (event) {
    const rect = canvas.getBoundingClientRect();
    const point = {
        x: event.clientX - rect.left,
        y: event.clientY - rect.top,
    };
    // 스케일과 오프셋을 고려한 마우스 좌표 변환
    const transformedPoint = {
        x: (point.x - offsetX - canvas.width / 2) / scale + canvas.width / 2,
        y: (point.y - offsetY - canvas.height / 2) / scale + canvas.height / 2,
    };
    let polygon = getPolygon(transformedPoint);
    if (polygon) console.log(polygon.name);
});
function preSetupCtx() {
    ctx.translate(offsetX, offsetY);
    ctx.translate(canvas.width / 2, canvas.height / 2);
    ctx.scale(scale, scale); // 줌 기능
    ctx.translate(-canvas.width / 2, -canvas.height / 2);
}
// 그려진 폴리곤들 + 마커를 캔버스에 그립니다.
function drawGangneung() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    drawGangneungFill();
    drawGangneungStroke();
    // drawMarkAndImages();
}

function drawGangneungFill() {
    drawnPolygons.forEach(function (polygon) {
        polygon.paths.forEach(function (path) {
            ctx.save();
            preSetupCtx();
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
            ctx.fillStyle = colorMap.get(polygon.name);
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
                    preSetupCtx();
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
let imageLoaded = false;
function drawMarkAndImages() {
    // Create a promise for each marker image loading
    if(!imageLoaded) {
        const promises = [
            ...markerImages.map((marker) => {
                return new Promise((resolve, reject) => {
                    marker.image = new Image();
                    marker.image.src = marker.imgSrc;
                    marker.image.onload = () => {
                        resolve(true);
                    };
                    marker.image.onerror = (err) => {
                        reject(err);
                    };
                });
            }),
            ...staticImages.map((staticImage) => {
                return new Promise((resolve, reject) => {
                    staticImage.image = new Image();
                    staticImage.image.src = staticImage.imgSrc;
                    staticImage.image.onload = () => {
                        resolve(true);
                    };
                    staticImage.image.onerror = (err) => {
                        reject(err);
                    };
                });
            })
        ];
        Promise.all(promises).then(() => {
            imageLoaded = true;
            drawMarkAndImagesInternal();
        }).catch((err) => {
            console.error('Error loading one or more images:', err);
        });
    } else {
        drawMarkAndImagesInternal();
    }
}
function drawMarkAndImagesInternal() {
    // All images are loaded; now draw them
    travelInfos.filter((marker) => {
        if(marker.zoomLevel > zoomLevel) {
            return false;
        }
        if(selectedCategory.length === 0) {
            return true;
        }
        if(!marker.category) {
            return true;
        }
        return selectedCategory.includes(marker.category);
    }).forEach((marker) => {
        ctx.save();
        preSetupCtx();
        let markerImage = markerImages
            .find(markerImg => markerImg.category === marker.category);
        if(markerImage == null) {
            markerImage = markerImages
                .find(markerImg => markerImg.category === 'DISPLAY');
            // 분류되지 않은 경우 기본으로 전시를 보여준다.
        }
        console.log(markerImage);
        marker.x = lonToX(marker.longitude);
        marker.y = latToY(marker.latitude);
        ctx.drawImage(markerImage.image, marker.x, marker.y, MARKER_SIZE / scale, MARKER_SIZE / scale);
        ctx.restore();
    });
    staticImages.forEach((staticImage) => {
        ctx.save();
        preSetupCtx();
        const x = lonToX(staticImage.lon);
        const y = latToY(staticImage.lat);
        ctx.drawImage(staticImage.image, x, y, staticImage.width, staticImage.height);
        ctx.restore();
    });
}
drawGangneung();
let isZooming = false; // 줌 애니메이션이 실행 중인지 확인하는 플래그

function zoomIn() {
    if (zoomLevel < MAX_ZOOM_LEVEL && !isZooming) {
        zoomLevel++;
        animateZoom(1.2); // 스케일을 20% 확대 애니메이션
    }
}

function zoomOut() {
    if (zoomLevel > MIN_ZOOM_LEVEL && !isZooming) {
        zoomLevel--;
        animateZoom(1 / 1.2); // 스케일을 20% 축소 애니메이션
    }
}

function animateZoom(targetScale) {
    isZooming = true; // 줌 애니메이션이 시작되면 플래그를 true로 설정
    const duration = 300; // 애니메이션 지속 시간 (밀리초)
    const startTime = performance.now();
    const initialScale = scale;
    const scaleDifference = targetScale - 1;

    function animate(time) {
        const elapsedTime = time - startTime;
        const progress = Math.min(elapsedTime / duration, 1); // 진행률 (0에서 1 사이)

        // Easing function (ease-out)
        const easedProgress = Math.max(1 - Math.pow(1 - progress, 3),0);
        // 스케일 값 업데이트
        scale = initialScale * (1 + scaleDifference * easedProgress);

        drawGangneung(); // 매 프레임마다 캔버스 다시 그리기

        if (progress < 1) {
            requestAnimationFrame(animate); // 애니메이션 계속
        } else {
            isZooming = false; // 줌 애니메이션이 완료되면 플래그를 false로 설정
        }
    }

    requestAnimationFrame(animate); // 애니메이션 시작
}

// 드래그 시작
canvas.addEventListener("mousedown", function (e) {
    isDragging = true;
    startX = e.clientX - offsetX;
    startY = e.clientY - offsetY;
});

// 드래그 중
canvas.addEventListener("mousemove", function (e) {
    if (isDragging) {
        offsetX = e.clientX - startX;
        offsetY = e.clientY - startY;
        drawGangneung(); // 캔버스 내용 다시 그리기
    }
});

// 드래그 종료
canvas.addEventListener("mouseup", function () {
    isDragging = false;
});

// 드래그가 캔버스 밖에서 종료되었을 때 처리
canvas.addEventListener("mouseleave", function () {
    isDragging = false;
});

// 휠 이벤트로 줌 인/아웃 제어
canvas.addEventListener("wheel", function (e) {
    e.preventDefault(); // 기본 휠 동작(스크롤) 방지

    // e.deltaY > 0이면 휠이 아래로, e.deltaY < 0이면 휠이 위로 움직임
    if (e.deltaY < 0 && !isZooming) {
        zoomIn(); // 줌 인
    } else if (e.deltaY > 0 && !isZooming) {
        zoomOut(); // 줌 아웃
    }
});

function getMark(transformedPoint) {
    const foundMarks = [];
    travelInfos.forEach((mark) => {
        if(transformedPoint.x >= mark.x && transformedPoint.x <= mark.x + MARKER_SIZE / scale &&
        transformedPoint.y >= mark.y && transformedPoint.y <= mark.y + MARKER_SIZE / scale) {
            foundMarks.push(mark);
        }
    });
    return foundMarks;
}

canvas.addEventListener("click", function(e) {
    e.preventDefault();
    const rect = canvas.getBoundingClientRect();
    const point = {
        x: e.clientX - rect.left,
        y: e.clientY - rect.top,
    };
    // 스케일과 오프셋을 고려한 마우스 좌표 변환
    const transformedPoint = {
        x: (point.x - offsetX - canvas.width / 2) / scale + canvas.width / 2,
        y: (point.y - offsetY - canvas.height / 2) / scale + canvas.height / 2,
    };
    console.log("lon", xToLon(transformedPoint.x));
    console.log("lat", yToLat(transformedPoint.y));
    const foundMarks = getMark(transformedPoint);
    const mainMarkerTooltip = document.querySelector("#main-marker-tooltip");
    mainMarkerTooltip.replaceChildren();
    if(foundMarks.length == 1) {
        // main 상세 정보에 보여주기
        getOutline(foundMarks[0].travelNo);
    } else if(foundMarks.length > 1) {
        foundMarks.slice(0, 5).forEach((foundMark) => {
            const liElement = document.createElement("li");
            const pElement = document.createElement("p");
            pElement.textContent = foundMark.travelName;
            liElement.appendChild(pElement);
            liElement.addEventListener("click", (e) => {
                e.preventDefault();
                getOutline(foundMark.travelNo);
                mainMarkerTooltip.replaceChildren();
            })
            mainMarkerTooltip.appendChild(liElement);
        });
        mainMarkerTooltip.style.top = point.y + 'px';
        mainMarkerTooltip.style.left = point.x + 'px';
    }
    console.log(foundMarks.length, foundMarks);
})