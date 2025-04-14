import { ArrowUp, ArrowDown } from 'lucide-react';

export default function Post({ postData }) {
    return (
        <div className="bg-gray-700 p-4 rounded shadow text-white w-full max-w-3xl mx-auto " >
            <div className="bg-white text-black p-2 rounded mb-2">{postData.title}</div>

            <div className="bg-white h-48 rounded mb-2 flex items-center justify-center text-black">
                <img src={postData.image} alt={postData.title} className="w-full h-full object-cover" />
            </div>

            <div className="flex justify-between items-center px-1">
                <div className="flex gap-2">
                    <button className="bg-green-500 hover:bg-green-600 text-white p-1 rounded shadow">
                        <ArrowUp size={20} />
                    </button>

                    <div className="flex items-center gap-1">
                        <span className="text-black">{postData.upvotes - postData.downvotes}</span>
                    </div>

                    <button className="bg-red-500 hover:bg-red-600 text-white p-1 rounded shadow">
                        <ArrowDown size={20} />
                    </button>
                </div>

                <span className="bg-white text-black px-3 py-1 rounded shadow text-sm font-medium">
                    {postData.state}
                </span>
            </div>
        </div>
    );
}
