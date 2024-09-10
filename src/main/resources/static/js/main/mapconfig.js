let zoomLevel = 7;
const MIN_ZOOM_LEVEL = 1;
const MAX_ZOOM_LEVEL = 14;
const colorMap = new Map();
colorMap.set("주문진권", "#FFFBD4");
colorMap.set("대관령권", "#D6F8FF");
colorMap.set("시내권", "#FFE3F3");
colorMap.set("정동진옥계권", "#FFF2E7");
colorMap.set("경포권", "#DBFFEA");
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
    {
        imgSrc: "/img/cuttypotato.png",
        width: 100,
        height: 100,
        lon: 128.83988367690708,
        lat: 37.659234104113516
    }
];
const MARKER_SIZE = 25;