import { useState } from "react";
import { ArrowUp, ArrowDown, Check } from "lucide-react";
import { mockData } from "@/app/mockData";  // Import mock data

export default function AnswersSection() {
    const [answerVotes, setAnswerVotes] = useState(mockData.commentsExamples.map((answer) => answer.votes));
    const [acceptedAnswer, setAcceptedAnswer] = useState(mockData.commentsExamples.findIndex((answer) => answer.accepted));

    return (
        <div>
            {mockData.commentsExamples.map((answer, index) => (
                <div
                    key={answer.id}
                    className={`bg-white p-6 border ${acceptedAnswer === index ? "border-green-500" : "border-gray-200"} rounded-md mb-4`}
                >
                    <div className="flex gap-4">
                        {/* Voting */}
                        <div className="flex flex-col items-center">
                            <button
                                onClick={() => {
                                    const newVotes = [...answerVotes];
                                    newVotes[index] += 1;
                                    setAnswerVotes(newVotes);
                                }}
                                className="text-gray-500 hover:text-orange-500"
                            >
                                <ArrowUp size={36} />
                            </button>
                            <span className="text-xl font-bold my-2">{answerVotes[index]}</span>
                            <button
                                onClick={() => {
                                    const newVotes = [...answerVotes];
                                    newVotes[index] -= 1;
                                    setAnswerVotes(newVotes);
                                }}
                                className="text-gray-500 hover:text-gray-700"
                            >
                                <ArrowDown size={36} />
                            </button>
                            <button
                                onClick={() => setAcceptedAnswer(index)}
                                className={`mt-2 ${acceptedAnswer === index ? "text-green-500" : "text-gray-400 hover:text-green-500"}`}
                            >
                                <Check size={18} />
                            </button>
                        </div>

                        {/* Answer content */}
                        <div className="flex-1">
                            <div className="prose max-w-none">
                                <p>{answer.content}</p>
                            </div>
                        </div>
                    </div>
                </div>
            ))}
        </div>
    );
}
