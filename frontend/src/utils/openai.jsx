// utils/openai.js
import axios from "axios";

const API_KEY = process.env.OPENAI_API_KEY;
const MODEL = "gpt-4o-mini";

const PRICE_INPUT = 0.15 / 1_000_000;
const PRICE_CACHED_INPUT = 0.075 / 1_000_000;
const PRICE_OUTPUT = 0.6 / 1_000_000;

export async function generatePostAI(content) {
    const prompt = `
Based on the following post content, propose:

- A **concise summary** in less than 50 words using the first person
- A **relevant title** in English

Respond in JSON format like:
{
  "title": "...",
  "summary": "...",
}

Content:
${content}
  `.trim();

    try {
        const response = await axios.post(
            "https://api.openai.com/v1/chat/completions",
            {
                model: MODEL,
                messages: [{ role: "user", content: prompt }],
                temperature: 0.7,
                max_tokens: 200,
            },
            {
                headers: {
                    Authorization: `Bearer ${API_KEY}`,
                    "Content-Type": "application/json",
                },
            }
        );

        const { usage, choices } = response.data;
        const { prompt_tokens, completion_tokens } = usage;

        const cost =
            prompt_tokens * PRICE_INPUT +
            prompt_tokens * PRICE_CACHED_INPUT +
            completion_tokens * PRICE_OUTPUT;

        console.log("📊 Estimated cost: $", cost.toFixed(6));

        const parsed = JSON.parse(choices[0].message.content);

        return {
            data: parsed,
            usage: {
                prompt_tokens,
                completion_tokens,
                totalCost: cost,
            },
        };
    } catch (err) {
        console.error("❌ OpenAI error:", err?.response?.data || err.message);
        throw new Error("Failed to generate post metadata");
    }
}
