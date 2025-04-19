import { ArrowUp, ArrowDown, Bookmark } from "lucide-react";
import Link from "next/link"


export default function Post({ postData }) {

    console.log(postData);
    //console.log("Consola test" +postData.content);
    return (

        <div className="bg-white rounded-lg shadow-md hover:shadow-xl transition-shadow duration-300 p-6 flex flex-col gap-6 mb-4">

            <div className="flex-1">
                <h2 className="text-2xl font-semibold text-gray-900 mb-4">{postData.title}</h2>

                <Link href={`/post/${postData.id}`} >
                    <div className="w-full h-auto bg-gray-200 rounded-lg overflow-hidden">
                        <img
                            src={postData.imageURL}
                            className="w-full h-full object-cover"
                            alt="Post Image"
                        />
                    </div>
                </Link>

                {/* TODO SHORT VERSION OF THE CONTENT (WE CAN JUST TAKE THE 100 FIRST CHARACTERS)*/}
                <p className="text-gray-700 mb-4 line-clamp-3">
                    {/* Max chars in content == 100*/}
                    {postData.content.length > 100 ? `${postData.content.slice(0, 100)}...` : postData.content}
                </p>

                <div className="flex flex-wrap gap-2 mb-4">
                    {postData.tags && postData.tags.map((tag, index) => (
                        <span key={index} className="bg-blue-100 text-blue-800 px-3 py-1 rounded-full text-sm font-medium">
                            {tag}
                        </span>
                    ))}
                </div>
                <div className=" flex flex-row justify-between">

                    <div className="flex flex-row w-1/4 items-center justify-between ">
                        <button
                            onClick={() => { }}
                            className="text-gray-600 hover:text-green-500"
                        >
                            <ArrowUp size={28} />
                        </button>
                        <span className="font-semibold text-lg text-gray-900">{postData.likes}</span>
                        <button
                            onClick={() => { }}
                            className="text-gray-600 hover:text-red-500"
                        >
                            <ArrowDown size={28} />
                        </button>
                        <button
                            onClick={() => { }}
                            className="text-gray-400 hover:text-yellow-500"
                        >
                            <Bookmark size={28} />
                        </button>
                    </div>

                    <div className="flex justify-between items-center text-sm text-gray-500 ">
                        <div className="flex items-center gap-2">
                            {/* TODO POST COMMENT COUNT*/}
                            <span>{postData.comments || "N/A"} comments</span>
                        </div>
                        <div className="flex items-center gap-2 text-xs ml-2">
                            {/* TODO POST AUTHOR USERNAME*/}
                            <span className="text-gray-600">{postData.author || "N/A"}</span>
                            <span>•</span>
                            {/* TODO POST CREATED AT*/}
                            <span className="text-gray-400">{postData.createdAt || "N/A"}</span>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    );
}
