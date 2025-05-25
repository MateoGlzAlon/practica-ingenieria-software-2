import { NextResponse } from 'next/server';
import { generatePostAI } from '@/utils/openai';

export async function POST(req) {
    const { content } = await req.json();

    try {
        const result = await generatePostAI(content);
        return NextResponse.json(result);
    } catch (error) {
        return NextResponse.json({ error: 'AI generation failed' }, { status: 500 });
    }
}
