FROM node:11.15.0-alpine as node

WORKDIR /app

COPY package.json ./

RUN npm install
RUN npm link @angular/cli

COPY . .

RUN npm run build

EXPOSE 4200

CMD ng serve --host 0.0.0.0 --disable-host-check 