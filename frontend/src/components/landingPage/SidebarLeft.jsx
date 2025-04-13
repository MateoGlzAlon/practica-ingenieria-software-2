export default function SidebarLeft() {
    return (
        <div className="w-[20%] min-w-[180px] flex flex-col gap-4 border-r-2 border-black">
            <div className="bg-white p-4 rounded shadow">Tag</div>
            <div className="bg-white p-4 rounded shadow">Filters</div>
            <div className="bg-white p-4 rounded shadow">About</div>
        </div>
    );
}
