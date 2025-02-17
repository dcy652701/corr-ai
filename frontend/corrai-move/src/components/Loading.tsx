const Loading = () => {
  return (
    <div className="flex items-center justify-center min-h-[200px]">
      <div className="relative w-24 h-24">
        {/* 外环 */}
        <div className="absolute w-full h-full rounded-full border-4 border-t-[#ffac06] border-r-[#ffac06/30] border-b-[#ffac06/10] border-l-[#ffac06/50] animate-spin-slow"></div>
        
        {/* 内环 */}
        <div className="absolute top-2 left-2 w-20 h-20 rounded-full border-4 border-t-[#ffac06/80] border-r-[#ffac06/20] border-b-[#ffac06/5] border-l-[#ffac06/40] animate-spin-reverse"></div>
        
        {/* 中心点 */}
        <div className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 w-3 h-3 bg-[#ffac06] rounded-full animate-pulse"></div>
      </div>
    </div>
  );
};

export default Loading; 