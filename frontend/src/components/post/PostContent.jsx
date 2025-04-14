import { ArrowUp, ArrowDown, Bookmark } from "lucide-react";
import { mockData } from "@/app/mockData"; // Import the mock data

export default function PostContent({ questionVotes, setQuestionVotes }) {

    const data = mockData.postExampleDetails;

    return (
        <div className="bg-white p-6 border border-gray-200 rounded-md mb-4">
            <div className="flex gap-4">
                <div className="flex flex-col items-center">
                    <button
                        onClick={() => setQuestionVotes(questionVotes + 1)}
                        className="text-gray-500 hover:text-green-700"
                    >
                        <ArrowUp size={36} />
                    </button>
                    <span className="text-xl font-bold my-2">{questionVotes}</span>
                    <button
                        onClick={() => setQuestionVotes(questionVotes - 1)}
                        className="text-gray-500 hover:text-red-700"
                    >
                        <ArrowDown size={36} />
                    </button>
                </div>

                <div className="flex-1">
                    <div className="prose max-w-none">
                        <h2 className="text-xl font-semibold">{data.question.title}</h2>
                        <p>{data.question.content}</p>

                        <pre className="bg-gray-100 p-4 rounded-md overflow-x-auto">
                            <code>{data.codeExample}</code>
                        </pre>
                    </div>
                </div>
            </div>
        </div>
    );
}
