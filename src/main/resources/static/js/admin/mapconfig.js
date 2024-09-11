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
    {
        imgSrc: '/img/DAEGWALLYEONG_txt.png',
        width: 55,
        height: 15,
        lon: 128.7792951791147,
        lat: 37.65210823896524
    },
    {
        imgSrc: '/img/CITY_txt.png',
        width: 41,
        height: 15,
        lon: 128.86674195540854 ,
        lat: 37.7299530805483
    },
    {
        imgSrc: '/img/JUMUNJIN_txt.png',
        width: 55,
        height: 15,
        lon: 128.70662813965927 ,
        lat: 37.83997378998569
    },
    {
        imgSrc: '/img/GYEONGPO_txt.png',
        width: 35,
        height: 35,
        lon: 128.89383813961228 ,
        lat: 37.79845654114139
    },
    {
        imgSrc: '/img/JEONGDONGJIN_txt.png',
        width: 55,
        height: 15,
        lon: 128.94064063960053,
        lat: 37.636539270648626
    }
];
const MARKER_SIZE = 25;