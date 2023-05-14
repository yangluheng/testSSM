import { sidebar } from "vuepress-theme-hope";

export const zhSidebar = sidebar({
  "/zh/": [
    "",
    {
      icon: "note",
      text: "Java基础",
      prefix: "page/Java基础/",
      link: "page/",
      children: "structure",
    },
    {
      text: "JVM",
      icon: "note",
      prefix: "page/JVM/",
      children: "structure",
    },
  ],
});
