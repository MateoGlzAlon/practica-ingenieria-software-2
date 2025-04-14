import Link from "next/link";
import { mockData } from "@/app/mockData";

export default function Sidebar() {
    return (
        <div className="space-y-2 w-[10%]">
            {
                mockData.postSideBarTags.map((tag) => (
                    <Link href="#" key={tag} className="pl-1.5 w-full hover:bg-gray-200 py-2 flex">
                        {tag}
                    </Link>
                ))
            }
        </div>
    );
}
