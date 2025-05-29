import getAllTags from "@/api/getAllTags";
import { useEffect, useState } from "react";
import { useTagFilter } from "@/hooks/tagsContext";

export default function SidebarLeft() {
    const [tags, setTags] = useState([]);
    const { selectedTags, setSelectedTags } = useTagFilter();

    useEffect(() => {
        const fetchTags = async () => {
            try {
                const data = await getAllTags();
                setTags(data.tags);

                // Set all tags as selected by default only if none are selected
                if (selectedTags.length === 0) {
                    setSelectedTags(data.tags);
                }
            } catch (error) {
                console.error("Error fetching tags:", error);
            }
        };
        fetchTags();
    }, []);

    const toggleTag = (tag) => {
        setSelectedTags((prev) =>
            prev.includes(tag)
                ? prev.filter((t) => t !== tag)
                : [...prev, tag]
        );
    };

    return (
        <div className="w-[20%] min-w-[180px] flex flex-col gap-4 sticky top-20 self-start">
            <div className="bg-white p-4 rounded shadow">
                <h3 className="font-medium mb-2">Tags</h3>
                <div className="space-y-1">
                    {tags.map((tag) => (
                        <div
                            key={tag}
                            onClick={() => toggleTag(tag)}
                            className={`text-sm py-1 px-2 rounded cursor-pointer hover:bg-gray-100 ${selectedTags.includes(tag) ? "bg-blue-200 font-semibold" : ""
                                }`}
                        >
                            {tag}
                        </div>
                    ))}
                </div>
            </div>

            <div className="bg-white p-4 rounded shadow">
                <h3 className="font-medium mb-2">About</h3>
                <p className="text-sm text-gray-600">
                    A community for developers to share knowledge and help each other.
                </p>
            </div>
        </div>
    );
}
