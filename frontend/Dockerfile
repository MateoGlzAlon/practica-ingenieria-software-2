FROM node:18-alpine AS base

WORKDIR /app


FROM base AS deps
WORKDIR /app
COPY package.json package-lock.json ./ 
RUN npm install --frozen-lockfile


FROM base AS builder
WORKDIR /app
COPY --from=deps /app/node_modules ./node_modules
COPY . .  
RUN npm run build

FROM base AS runner
WORKDIR /app

ENV NODE_ENV=production

RUN addgroup --system --gid 1001 nodejs
RUN adduser --system --uid 1001 nextjs

COPY --from=builder /app/package.json ./package.json
COPY --from=builder /app/src/public ./public
COPY --from=builder --chown=nextjs:nodejs /app/.next/ ./.next
COPY --from=builder --chown=nextjs:nodejs /app/.next/static ./.next/static
COPY --from=builder /app/node_modules ./node_modules 

USER nextjs

EXPOSE 3000
ENV PORT=3000

CMD ["npm", "start"]
