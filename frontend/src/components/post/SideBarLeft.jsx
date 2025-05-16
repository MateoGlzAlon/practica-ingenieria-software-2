import Link from "next/link";
import { mockData } from "@/app/mockData";
import getAllTags from "@/api/getAllTags"
import { useEffect, useState } from "react"

export default function SidebarLeft() {

    const [tags, setTags] = useState([])
            
    const fetchTags = async () => {
        try {
            const data = await getAllTags()
            setTags(data.tags)
        } catch (error) {
            console.error("Error fetching tags:", error)
        }
    }

    useEffect(() => {
        fetchTags()
    }, [])


    
    return (
        <div className="space-y-2 w-[10%]">
            {
                tags.map((tag) => (
                    <Link href="#" key={tag} className="pl-1.5 w-full hover:bg-gray-200 py-2 flex">
                        {tag}
                    </Link>
                ))
            }
        </div>
    );
}
