let zoomLevel = 8;
const MIN_ZOOM_LEVEL = 1;
const MAX_ZOOM_LEVEL = 14;
const colorMap = new Map();
colorMap.set("주문진권", "#FFFCDE");
colorMap.set("대관령권", "#E5FCFB");
colorMap.set("시내권", "#FAF2FD");
colorMap.set("정동진옥계권", "#FDF2E9");
colorMap.set("경포권", "#E9FAD9");
const markerImages = [{
    imgSrc:'/img/DISPLAY_marker.png',
    category: 'DISPLAY'
},{
    imgSrc:'/img/HISTORY_marker.png',
    category: 'HISTORY'
},{
    imgSrc:'/img/LEISURE_marker.png',
    category: 'LEISURE'
},{
    imgSrc:'/img/MOUNTAIN_marker.png',
    category: 'MOUNTAIN'
},{
    imgSrc:'/img/PARK_marker.png',
    category: 'PARK'
},{
    imgSrc:'/img/SEA_marker.png',
    category: 'SEA'
}]
const staticImages = [

];
const MARKER_SIZE = 25;