


FROM node:20-alpine AS build
WORKDIR /app
COPY package*.json ./

RUN npm install --legacy-peer-deps
COPY . .
RUN npm run build

FROM node:20-alpine AS production
WORKDIR /app

COPY --from=build /app/dist/frontend ./dist/frontend
COPY --from=build /app/package*.json ./

RUN npm install --omit=dev --legacy-peer-deps

ENV NODE_ENV=production
EXPOSE 4000

CMD ["npm", "run", "serve:ssr:frontend"]