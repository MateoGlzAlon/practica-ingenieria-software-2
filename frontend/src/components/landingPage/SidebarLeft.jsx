
//import { mockData } from "@/app/mockData"
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
    
    //const tags = mockData.postSideBarTags

    return (
        <div className="w-[20%] min-w-[180px] flex flex-col gap-4 sticky top-20 self-start">
            <div className="bg-white p-4 rounded shadow">
                <h3 className="font-medium mb-2">Tags</h3>
                <div className="space-y-1">
                    {tags.map((tag) => (
                        <div key={tag} className="text-sm py-1 px-2 hover:bg-gray-100 rounded cursor-pointer">
                            {tag}
                        </div>
                    ))}
                </div>
            </div>

            <div className="bg-white p-4 rounded shadow">
                <h3 className="font-medium mb-2">Filters</h3>
                <div className="space-y-1">
                    <div className="text-sm py-1 px-2 hover:bg-gray-100 rounded cursor-pointer">Popular</div>
                    <div className="text-sm py-1 px-2 hover:bg-gray-100 rounded cursor-pointer">Recent</div>
                    <div className="text-sm py-1 px-2 hover:bg-gray-100 rounded cursor-pointer">Unanswered</div>
                </div>
            </div>

            <div className="bg-white p-4 rounded shadow">
                <h3 className="font-medium mb-2">About</h3>
                <p className="text-sm text-gray-600">A community for developers to share knowledge and help each other.</p>
            </div>
        </div>
    )

}
